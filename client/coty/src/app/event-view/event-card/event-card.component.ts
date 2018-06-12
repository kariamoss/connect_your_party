import {Component, Input, OnInit} from '@angular/core';
import {EventService} from "../../services/events.service";

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.css']
})
export class EventCardComponent implements OnInit{

  title;
  img_url;
  participants: number;
  location;
  color;
  type;
  @Input() id: number;


  constructor(private eventService: EventService) {

  }

  ngOnInit() {
    let event = this.eventService.getEventById(this.id);
    this.title = event.title;
    this.participants = event.people;
    this.location = event.location;
    this.color = {"background-color" : event.color};
    this.img_url = '../../../assets/'+event.photo;
    this.type = event.type;
  }

}
