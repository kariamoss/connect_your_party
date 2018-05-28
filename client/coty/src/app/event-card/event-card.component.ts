import {Component, Input, OnInit} from '@angular/core';
import {EventModel} from "../../model/EventModel";
import {EventService} from "../services/events.service";

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.css']
})
export class EventCardComponent implements OnInit {

  title;
  desc;
  participants: number;
  location;
  color;
  @Input() id: number;


  constructor(private eventService: EventService) {

  }

  ngOnInit() {
    let event = this.eventService.getEventById(this.id);
    this.title = event.title;
    this.participants = event.people;
    this.location = event.location;
    this.color = {"background-color" : event.color};
  }

}
