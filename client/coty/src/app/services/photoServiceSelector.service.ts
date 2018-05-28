import {Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";

@Injectable()
export class PhotoServiceSelectorService {

  servicesSubject = new Subject<any[]>();

  private services = [
    {
      name: 'Google Drive',
      icon: "http://icons.iconarchive.com/icons/marcus-roberto/google-play/64/Google-Drive-icon.png",
    },
    {
      name: 'Dropbox',
      icon: "http://icons.iconarchive.com/icons/danleech/simple/64/dropbox-icon.png",
    },
  ];

  emitServicesSubject() {
    this.servicesSubject.next(this.services.slice());
  }
}
