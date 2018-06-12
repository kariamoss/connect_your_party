import { Component, OnInit } from '@angular/core';
import {ParametersService} from "../services/parameters.service";
import {EventService} from "../services/events.service";
import {EventModel} from "../../model/event.model";
import {Subscription} from "rxjs/internal/Subscription";
import {Service} from "../../model/service.model";
import {UserService} from "../services/user.service";
import {UserModel} from "../../model/user.model";

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  id: number;
  event: EventModel;

  constructor(private parameters: ParametersService,
              private eventService: EventService) { }

  ngOnInit() {
    this.id = this.parameters.sharedId;
    this.event = this.eventService.getEventById(this.id);
  }

}
