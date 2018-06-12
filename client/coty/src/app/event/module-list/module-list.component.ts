import { Component, OnInit } from '@angular/core';
import {EventService} from "../../services/events.service";
import {ParametersService} from "../../services/parameters.service";

@Component({
  selector: 'app-module-list',
  templateUrl: './module-list.component.html',
  styleUrls: ['./module-list.component.css']
})
export class ModuleListComponent implements OnInit {

  selected;

  constructor(private eventS: EventService, private paramS: ParametersService) { }

  ngOnInit() {
    this.selected = 'infos';
  }

  setActive(toto){
    this.selected = toto;
  }

  isEnabled(){
    return this.eventS.isTypeOfParty(this.paramS.sharedId)
  }



}
