import {Component, Inject, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/internal/Subscription";
import {SelectorService} from "../services/selector.service";
import {Service} from "../../model/service.model";
import {APP_CONFIG, AppConfig} from "../app-config.module";
import {HttpClient, HttpHeaders} from "@angular/common/http";

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
  // canDisconnect: boolean = false;

  constructor(private selectorService: SelectorService,
              private httpClient: HttpClient,
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
    const headers = new HttpHeaders();
    headers.append("Content-Type", 'application/json');
    const result = this.httpClient.get<OAuthResult>('http://' + this.config.apiEndpoint +'/back-1.0-SNAPSHOT/retrieveOAuthURL/' + newService.name, {headers: headers});
    result.subscribe(data => {
      if (data.OAuthURL) {
        window.location.href = data.OAuthURL + '?' +
          'client_id=' + data.client_id +
          '&redirect_uri=http://localhost:4200/authentication/?service='+ newService.name +
          '&response_type=code' +
          '&state= ' + this.id +'/'+ this.module;
      }
    },
      error => console.log(error));
  }
/*
  forgetToken() {
    this.tokenRetriever.forgetToken(this.selectedService);
    this.selectorService.changeSelectedService(this.module, null);
    this.selectedService = null;
    this.canDisconnect = false;
  }
*/

  ngOnDestroy(): void {
    this.serviceSubscription.unsubscribe();
  }


}

interface OAuthResult {
  OAuthURL: string,
  client_id: string,
}
