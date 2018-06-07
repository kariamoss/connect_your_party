import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-module-list',
  templateUrl: './module-list.component.html',
  styleUrls: ['./module-list.component.css']
})
export class ModuleListComponent implements OnInit {

  selected;

  constructor() { }

  ngOnInit() {
    this.selected = 'infos';
  }

  setActive(toto){
    this.selected = toto;
  }



}
