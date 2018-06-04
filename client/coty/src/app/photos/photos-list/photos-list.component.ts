import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material";
import {PhotoDialogComponent} from "../photo-dialog/photo-dialog.component";
import {AddPhotoComponent} from "../add-photo/add-photo.component";
import {Subscription} from "rxjs/internal/Subscription";
import {PhotoService} from "../../services/photo.service";
import {Photo} from "../../../model/photo.model";
import {APP_CONFIG, AppConfig} from "../../app-config.module";
import {interval} from "rxjs/internal/observable/interval";

@Component({
  selector: 'app-photos-list',
  templateUrl: './photos-list.component.html',
  styleUrls: ['./photos-list.component.css']
})
export class PhotosListComponent implements OnInit, OnDestroy {

  photos: Photo[];
  photosSubscription: Subscription;
  module:string = 'photo';
  refreshSubscription: Subscription;

  constructor(public dialog: MatDialog,
              private photoService: PhotoService,
              @Inject(APP_CONFIG) public config: AppConfig,) { }

  ngOnInit() {
    this.photosSubscription = this.photoService.photosSubject.subscribe(
      (photos: Photo[]) => {
        this.photos = photos;
      }
    );
    this.photoService.getPhotos(1);
    this.refreshSubscription = interval(2000).subscribe(
      () => {
        this.photoService.getPhotos(1);
      },
      (error) => {
        console.log('An error occurred: ' + error);
      },
    );
  }

  showPhoto(src: string){
    let dialogRef = this.dialog.open(PhotoDialogComponent);
    dialogRef.componentInstance.url = src;
  }

  openAddDialog(): void {
    let dialogRef = this.dialog.open(AddPhotoComponent, {
      width: '600px',
    });
    dialogRef.componentInstance.module = this.module;
  }

  ngOnDestroy(): void {
    this.photosSubscription.unsubscribe();
    this.refreshSubscription.unsubscribe();
  }

}
