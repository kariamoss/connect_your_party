import {Subject} from "rxjs/index";
import {EventModel} from "../../model/event.model";

/**
 * Service managing the different events online (mocked)
 */
export class EventService{

  private colors: string[] = ["#fb607f", "#7cb9e8", "#c8e2f2", "#6cfbe2"];
  private events: EventModel[] = [
    new EventModel(1,'Soirée barbecue','Moultes bons moments à partager entre amis sont prévus. Ce sera le feu ! 😂😂😂 Ramène ta chipo !!',23,'chez la daronne à Lucas','9 juin','bbq.jpg','party',this.colors[0]),
    new EventModel(2,'Piscine','Lucas veut aller à la piscine pour entretenir son corps alors on est sympa on l\'accompagne pour qu\'il soit pas seul',4,'à la piscine municipale','18 mars','piscine.jpg','party',this.colors[1]),
    new EventModel(3,'Kermesse','on fait la fête pasqu\'on aura plus à voir la tête de lucas',1000,'à l\'ecole','18 juin','kermesse.jpg','event',this.colors[2])
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
