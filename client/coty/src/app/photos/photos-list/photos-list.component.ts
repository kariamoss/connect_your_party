import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material";
import {PhotoDialogComponent} from "../photo-dialog/photo-dialog.component";
import {HttpClient, HttpClientJsonpModule} from "@angular/common/http";
import {AddPhotoComponent} from "../add-photo/add-photo.component";

@Component({
  selector: 'app-photos-list',
  templateUrl: './photos-list.component.html',
  styleUrls: ['./photos-list.component.css']
})
export class PhotosListComponent implements OnInit {

  photos: string[];
  module: 'photo';

  constructor(public dialog: MatDialog, private httpClient : HttpClient) { }

  ngOnInit() {
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

}
