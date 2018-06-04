import {Component, Inject, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/internal/Subscription";
import {MatDialogRef} from "@angular/material";
import {SelectorService} from "../services/selector.service";
import {isNull, isUndefined} from "util";
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
        '&redirect_uri=http://localhost:4200/events/1/photos?service='+ newService.name +
        '&response_type=code';
    }
  }

  ngOnDestroy(): void {
    this.serviceSubscription.unsubscribe();
  }


}
