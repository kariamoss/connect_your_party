
import {Subject} from "rxjs/index";
import {EventModel} from "../../model/event.model";
export class EventService{

  private events: EventModel[] = [
    new EventModel(1,'event1',12,'chez la daronne de lucas','coral'),
    new EventModel(2,'event2',47,'at lucas\'s mom','lightblue'),
    new EventModel(3,'event3',94,'bei lukas mutter','lightgreen')
  ];
  eventSubject = new Subject<EventModel[]>();

  emitEvents() {
    this.eventSubject.next(this.events.slice());
  }

  addEvent(event: EventModel) {
    this.events.push(event);
    this.emitEvents();
  }

  getEventById(id: number): EventModel{
    const event = this.events.find(
      (eventObject) => {
        return eventObject.id === id;
      }
    );
    return event;
  }
}
