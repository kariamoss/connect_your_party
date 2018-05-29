import {Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class SelectorService {

  constructor(private httpClient: HttpClient) {}

  servicesSubject = new Subject<any[]>();

  private services = [
    {
      name: "ServiceName",
      icon: ""
    }
  ];

  private selectedServices = [
    {
      module: 'photo',
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

  updateServicesList(module: string) {
    const headers = new HttpHeaders();
    headers.append("Content-Type", 'application/json');
    const result = this.httpClient.get('http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotoServices', {headers: headers});
    result.subscribe(data => console.log(data));
  }

  emitServicesSubject(module: string) {
    this.servicesSubject.next(this.services.slice());
  }
}
