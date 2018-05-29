import {Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";

@Injectable()
export class SelectorService {

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

  private selectedServices = [
    {
      module: 'photos',
      service: null,
    },
  ];

  changeSelectedService(module: string, newService): void {
    const service = this.selectedServices.find(
      (serviceObject) => {
        return serviceObject.module === module;
      }
    );
    service.service = newService;
    console.log(this.selectedServices)
  }

  getSelectedService(module: string) {
    const service = this.selectedServices.find(
      (serviceObject) => {
        return serviceObject.module === module;
      }
    );
    return service.service;
  }

  emitServicesSubject(module: string) {
    this.servicesSubject.next(this.services.slice());
  }
}
