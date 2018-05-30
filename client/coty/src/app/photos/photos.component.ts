import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ParametersService} from "../services/parameters.service";
import {ServiceSelectorComponent} from "../service-selector/service-selector.component";
import {MatDialog} from "@angular/material";
import {SelectorService} from "../services/selector.service";

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css'],
})
export class PhotosComponent implements OnInit {

  id: number;

  constructor(private route: ActivatedRoute,
              private parameters: ParametersService,
              public dialog: MatDialog,
              private selectorService: SelectorService) { }

  ngOnInit() {
    this.id = this.parameters.sharedId;
    this.selectorService.updateServicesList('photo');
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(ServiceSelectorComponent, {
      width: '450px',
    });
    dialogRef.componentInstance.module = 'photo';

  }

}
