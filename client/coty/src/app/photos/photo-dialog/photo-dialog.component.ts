import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-photo-dialog',
  templateUrl: './photo-dialog.component.html',
  styleUrls: ['./photo-dialog.component.css']
})
export class PhotoDialogComponent implements OnInit {

  url: string;

  constructor() { }

  ngOnInit() {
  }

}
