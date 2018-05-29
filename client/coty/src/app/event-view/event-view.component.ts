import {Component, OnDestroy, OnInit} from '@angular/core';
import {EventModel} from "../../model/event.model";
import {Subscription} from "rxjs/index";
import {EventService} from "../services/events.service";

@Component({
  selector: 'app-event-view',
  templateUrl: './event-view.component.html',
  styleUrls: ['./event-view.component.css']
})
export class EventViewComponent implements OnInit, OnDestroy {

  events: EventModel[];
  eventsSubscription: Subscription;

  constructor(private eventService: EventService ) { }

  ngOnInit() {
    this.eventsSubscription = this.eventService.eventSubject.subscribe(
      (events: EventModel[]) => {
        this.events = events;
      }
    );
    this.eventService.emitEvents();
  }

  ngOnDestroy(): void{
    this.eventsSubscription.unsubscribe();
  }

}
