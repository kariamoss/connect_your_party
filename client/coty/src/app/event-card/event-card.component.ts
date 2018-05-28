import { Component, OnInit } from '@angular/core';
import {EventModel} from "../../model/EventModel";

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.css']
})
export class EventCardComponent implements OnInit {

  title;
  desc;
  participants;
  location;
  color;


  constructor() {
    let model = new EventModel();
    this.title = model.getTitle();
    this.desc = model.getDescription();
    this.participants = model.getNbParticipants();
    this.location = model.getLocation();
    this.color = {"background-color" : model.getColorTheme()};
  }

  ngOnInit() {
  }

}
