import { Component, OnInit } from '@angular/core';
import {ParametersService} from "../services/parameters.service";
import {EventService} from "../services/events.service";
import {EventModel} from "../../model/event.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  id: number;
  title: string;
  people: number;
  location: string;

  constructor(private route: ActivatedRoute,
              private eventService: EventService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

}
