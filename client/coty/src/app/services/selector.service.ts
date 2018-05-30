import {Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Service} from "../../model/service.model";
import {isUndefined} from "util";

@Injectable()
export class SelectorService {

  constructor(private httpClient: HttpClient) {
  }

  servicesSubject = new Subject<Service[]>();

  private services: Array<Module> = [
    {
      module: 'photo',
      selectedService: null,
      moduleServices: [],
    },
  ];

  changeSelectedService(module: string, newService): void {
    const selection = this.getModule(module);
    selection.selectedService = newService;
  }

  getSelectedService(module: string) {
    const selection = this.getModule(module);
    return selection.selectedService;
  }

  updateServicesList(module: string) {
    const headers = new HttpHeaders();
    headers.append("Content-Type", 'application/json');
    const result = this.httpClient.get<Array<Service>>('http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotoServices', {headers: headers});
    result.subscribe(data => {
      this.merge(data, module);
    });
    this.emitServicesSubject(module);
  }

  private merge(data: Array<Service>, module: string) {
    let serviceList = this.getModule(module).moduleServices;
    for (let i = serviceList.length - 1; i >= 0; i--) {
      if (data.map(x => x.name).indexOf(serviceList[i].name) === -1) {
        if (this.getModule(module).selectedService === serviceList[i])
          this.getModule(module).selectedService = {name: 'Aucun service sélectionné'};
        serviceList.splice(i, 1);
      }
    }
    while (data.length > 0) {
      const elt = data.pop();
      const index = serviceList.map(x => x.name).indexOf(elt.name);
      if (index === -1) {
        serviceList.push(elt);
      }
    }

  }

  getModule(module: string) {
    const tmp = this.services.find(
      (serviceObject) => {
        return serviceObject.module === module;
      }
    );
    if (isUndefined(tmp)) {
      this.services.push({
        module: module,
        selectedService: null,
        moduleServices: [],
      });
      return this.getModule(module);
    }
    return tmp;
  }

  emitServicesSubject(module: string) {
    this.servicesSubject.next(this.getModule(module).moduleServices.slice());
  }
}

export interface Module {
  module: string;
  selectedService: Service;
  moduleServices: Array<Service>;
}
