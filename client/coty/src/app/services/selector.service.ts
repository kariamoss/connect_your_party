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
    while (data.length > 0) {
      const elt = data.pop();
      const index = this.getModule(module).moduleServices.map(x=>x.name).indexOf(elt.name);
      if (index === -1) {
        this.getModule(module).moduleServices.push(elt);
      }
      // ToDo : remove entries that are not anymore in data
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
