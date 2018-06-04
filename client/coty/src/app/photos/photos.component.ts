import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ParametersService} from "../services/parameters.service";
import {ServiceSelectorComponent} from "../service-selector/service-selector.component";
import {MatDialog} from "@angular/material";
import {SelectorService} from "../services/selector.service";
import {Subscription} from "rxjs/internal/Subscription";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {isUndefined} from "util";
import {Service} from "../../model/service.model";

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css'],
})
export class PhotosComponent implements OnInit, OnDestroy {

  id: number;
  module: string = 'photo';
  sub: Subscription;
  queryService: Service;
  code;
  token;

  constructor(private route: ActivatedRoute,
              private parameters: ParametersService,
              private httpClient: HttpClient,
              public dialog: MatDialog,
              private selectorService: SelectorService,) { }

  ngOnInit() {
    this.id = this.parameters.sharedId;
    this.selectorService.updateServicesList(this.module);
    setTimeout(() => {
      this.sub = this.route.queryParams.subscribe(params => {
        this.queryService = this.selectorService.getServiceByName(this.module, params.service);
        this.selectorService.changeSelectedService(this.module, this.queryService);
        this.code = params.code;
        if (!isUndefined(this.code)) {
          let formData: FormData = new FormData();
          formData.append('code', this.code);
          formData.append('grant_type', 'authorization_code');
          formData.append('client_id', this.queryService.client_id);
          formData.append('client_secret', this.queryService.client_secret);
          formData.append('redirect_uri', 'http://localhost:4200/events/'+ this.id +'/photos?service=' + this.queryService.name);
          let headers = new HttpHeaders();
          headers.append('Content-Type', 'application/x-www-form-urlencoded');
          this.httpClient.post('https://' + this.queryService.oAuthTokenURL, formData, {headers: headers})
            .subscribe(
              data => {
                console.log(data);
              },
              error => console.log(error)
            );
        }

      });
    }, 1000);
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(ServiceSelectorComponent, {
      width: '450px',
    });
    dialogRef.componentInstance.module = this.module;
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }



}
