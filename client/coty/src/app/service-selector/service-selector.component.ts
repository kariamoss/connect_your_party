import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/internal/Subscription";
import {MatDialogRef} from "@angular/material";
import {SelectorService} from "../services/selector.service";
import {isNull, isUndefined} from "util";
import {Service} from "../../model/service.model";

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

  constructor(private selectorService: SelectorService,) { }

  ngOnInit() {
    this.serviceSubscription = this.selectorService.servicesSubject.subscribe(
      (services: Service[]) => {
        this.services = services;
      }
    );
    this.selectorService.emitServicesSubject(this.module);
    this.selectedService = this.selectorService.getSelectedService(this.module);
  }

  onChanges(newService): void {
    this.selectorService.changeSelectedService(this.module, newService);
  }

  ngOnDestroy(): void {
    this.serviceSubscription.unsubscribe();
  }


}
