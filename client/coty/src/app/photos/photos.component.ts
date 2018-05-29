import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ParametersService} from "../services/parameters.service";

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnInit {

  id: number;

  constructor(private route: ActivatedRoute,
              private parameters: ParametersService) { }

  ngOnInit() {
    this.id = this.parameters.sharedId;
  }

}
