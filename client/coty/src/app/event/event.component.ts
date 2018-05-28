import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ParametersService} from "../services/parameters.service";
import {EventService} from "../services/events.service";
import {isUndefined} from "util";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css'],
  providers: [ParametersService]
})
export class EventComponent implements OnInit {

  id: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private parameters: ParametersService,
              private eventService: EventService) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    if (isUndefined(this.eventService.getEventById(+this.id))) {
      this.router.navigate(['/not-found']);
      return;
    }
    this.parameters.sharedId = this.id;
  }

}
