import {Component, Inject, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/internal/Subscription";
import {SelectorService} from "../services/selector.service";
import {Service} from "../../model/service.model";
import {APP_CONFIG, AppConfig} from "../app-config.module";
import {TokenRetrieverService} from "../services/tokenRetriever.service";

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
  canDisconnect: boolean = false;

  constructor(private selectorService: SelectorService,
              private tokenRetriever: TokenRetrieverService,
              @Inject(APP_CONFIG) private config: AppConfig,) { }

  ngOnInit() {
    this.serviceSubscription = this.selectorService.servicesSubject.subscribe(
      (services: Service[]) => {
        this.services = services;
      }
    );
    this.selectorService.emitServicesSubject(this.module);
    this.selectedService = this.selectorService.getSelectedService(this.module);
    this.canDisconnect = !!this.tokenRetriever.getToken(this.selectedService);
  }

  onChanges(newService: Service): void {
    this.selectorService.changeSelectedService(this.module, newService);
    if (newService.oAuthURL && !this.tokenRetriever.getToken(newService)) {
      window.location.href = "https://" + newService.oAuthURL + '?' +
        'client_id=' + newService.client_id +
        '&redirect_uri=http://localhost:4200/authentication/?service='+ newService.name +
        '&response_type=code' +
        '&state= ' + this.id +'/'+ this.module;
    }
  }

  forgetToken() {
    this.tokenRetriever.forgetToken(this.selectedService);
    this.selectorService.changeSelectedService(this.module, null);
    this.selectedService = null;
    this.canDisconnect = false;
  }

  ngOnDestroy(): void {
    this.serviceSubscription.unsubscribe();
  }


}
