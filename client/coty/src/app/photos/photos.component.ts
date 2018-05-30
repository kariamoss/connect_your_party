import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ParametersService} from "../services/parameters.service";
import {ServiceSelectorComponent} from "../service-selector/service-selector.component";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css'],
})
export class PhotosComponent implements OnInit {

  id: number;

  constructor(private route: ActivatedRoute,
              private parameters: ParametersService,
              public dialog: MatDialog, ) { }

  ngOnInit() {
    this.id = this.parameters.sharedId;
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(ServiceSelectorComponent, {
      width: '450px',
    });
    dialogRef.componentInstance.module = 'photos';

  }

}
