import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material";
import {PhotoDialogComponent} from "../photo-dialog/photo-dialog.component";

@Component({
  selector: 'app-photos-list',
  templateUrl: './photos-list.component.html',
  styleUrls: ['./photos-list.component.css']
})
export class PhotosListComponent implements OnInit {

  photos: string[] = [
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
    'http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos',
  ];

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  showPhoto(src: string){
    let dialogRef = this.dialog.open(PhotoDialogComponent);
    dialogRef.componentInstance.url = src;

  }

}
