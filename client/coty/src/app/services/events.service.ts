import {Subject} from "rxjs/index";
import {EventModel} from "../../model/event.model";

/**
 * Service managing the different events online (mocked)
 */
export class EventService{

  private colors: string[] = ["#fb607f", "#7cb9e8", "#c8e2f2", "#6cfbe2"];
  private events: EventModel[] = [
    new EventModel(1,'Soirée barbecue','Moultes bons moments à partager entre amis sont prévus Ce sera le feu ! 😂😂😂 Ramène ta chipo !!',23,'chez la daronne à Lucas','9 juin',this.colors[0]),
    new EventModel(2,'Piscine','Lucas veut aller à la piscine pour entretenir son corps alors on est sympa on l\'accompagne pour qu\'il soit pas seul',4,'a la piscine municipale','18 mars',this.colors[1]),
    new EventModel(3,'Aprem Bowling','on va faire du bowling quoi',17,'au bowling lol','Demain',this.colors[2])
  ];
  eventSubject = new Subject<EventModel[]>();

  emitEvents() {
    this.eventSubject.next(this.events.slice());
  }

  addEvent(event: EventModel) {
    this.events.push(event);
    this.emitEvents();
  }

  getEventById(id): EventModel{
    const event = this.events.find(
      (eventObject) => {
        return eventObject.id == id;
      }
    );
    return event;
  }
}
