import {Component, Inject, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/internal/Subscription";
import {SelectorService} from "../services/selector.service";
import {Service} from "../../model/service.model";
import {APP_CONFIG, AppConfig} from "../app-config.module";

@Component({
  selector: 'app-service-selector',
  templateUrl: './service-selector.component.html',
  styleUrls: ['./service-selector.component.css']
})
export class ServiceSelectorComponent implements OnInit, OnDestroy {

  @Input()
  module: string;
  services: Service[];
  serviceSubscription: Subscription;
  @Input()
  selectedService;
  @Input()
  id: number;

  constructor(private selectorService: SelectorService,
              @Inject(APP_CONFIG) private config: AppConfig,) { }

  ngOnInit() {
    this.serviceSubscription = this.selectorService.servicesSubject.subscribe(
      (services: Service[]) => {
        this.services = services;
      }
    );
    this.selectorService.emitServicesSubject(this.module);
    this.selectedService = this.selectorService.getSelectedService(this.module);
  }

  onChanges(newService: Service): void {
    this.selectorService.changeSelectedService(this.module, newService);
    if (newService.oAuthURL) {
      window.location.href = "https://" + newService.oAuthURL + '?' +
        'client_id=' + newService.client_id +
        '&redirect_uri=http://localhost:4200/authentication/?service='+ newService.name +
        '&response_type=code' +
        '&state= ' + this.id +'/'+ this.module;
    }
  }

  ngOnDestroy(): void {
    this.serviceSubscription.unsubscribe();
  }


}
