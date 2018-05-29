
import {Subject} from "rxjs/index";
import {EventModel} from "../../model/event.model";
export class EventService{

  private colors: string[] = ["#fb607f", "#7cb9e8", "#c8e2f2", "#6cfbe2"];
  private events: EventModel[] = [
    new EventModel(1,'event1',12,'chez rachel',this.colors[0]),
    new EventModel(2,'event2',47,'at lucas\'s mom',this.colors[1]),
    new EventModel(3,'event3',94,'bei lukas mutter',this.colors[2])
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
