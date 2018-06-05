import {Inject, Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Service} from "../../model/service.model";
import {isUndefined} from "util";
import {APP_CONFIG, AppConfig} from "../app-config.module";

/**
 * Service managing services selections for each module.
 * services: Array<Module> contains Module interface object containing the selectedService
 */
@Injectable()
export class SelectorService {

  constructor(private httpClient: HttpClient,
              @Inject(APP_CONFIG) public config: AppConfig,) {
  }

  servicesSubject = new Subject<Service[]>();

  private services: Array<Module> = [];

  changeSelectedService(module: string, newService): void {
    const selection = this.getModule(module);
    selection.selectedService = newService;
  }

  getServiceByName(module: string, serviceName: string): any {
    const selection = this.getModule(module);
    return selection.moduleServices.find(
      (serviceObject) => {
        return serviceObject.name === serviceName;
      }
    );
  }

  getSelectedService(module: string) {
    const selection = this.getModule(module);
    return selection.selectedService;
  }

  /**
   * Get the services from the specified module.
   * Merge the data received and the data stored on the front end. Services no longer available are removed
   * and new services are added.
   * @param {string} module
   */
  updateServicesList(module: string) {
    const headers = new HttpHeaders();
    headers.append("Content-Type", 'application/json');
    const result = this.httpClient.get<Array<Service>>('http://' + this.config.apiEndpoint +'/back-1.0-SNAPSHOT/'+ module +'/getServices', {headers: headers});
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
          this.getModule(module).selectedService = null;
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

  /**
   * Look for a module stored on the front side. If the module doesn't exist we add a new Module to services.
   * (we supposed that module requested should exist on the backend)
   * @param {string} module
   * @returns {Module}
   */
  getModule(module: string): Module {
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
