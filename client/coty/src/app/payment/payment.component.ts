import {Component, Inject, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {EventService} from "../services/events.service";
import {ParametersService} from "../services/parameters.service";
import {HttpClient} from "@angular/common/http";
import {APP_CONFIG, AppConfig} from "../app-config.module";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material";
import {AddPhotoComponent} from "../photos/add-photo/add-photo.component";
import {ServiceSelectorComponent} from "../service-selector/service-selector.component";
import {SelectorService} from "../services/selector.service";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  module: string = 'payment';
  totalCost: number;
  pricepp: number;
  participants: number;
  costList: any[] = [
    {
      name: 'des chips',
      price: 15
    },
    {
      name: 'du coca',
      price: 10
    },
    {
      name: 'de la drogue',
      price: 200
    }];

  constructor(public userS: UserService,
              private eventS: EventService,
              private paramS: ParametersService,
              private httpClient: HttpClient,
              @Inject(APP_CONFIG) private config: AppConfig,
              public dialog: MatDialog,
              private router: Router,
              private selectorService: SelectorService,
              private parameters: ParametersService
  ) {
  }

  ngOnInit() {
    this.participants = this.eventS.getEventById(this.paramS.sharedId).people;
    this.update();
  }

  update() {
    this.pricepp = 0.0;
    this.totalCost = 0;
    for (let i = 0; i < this.costList.length; i++) {
      this.totalCost += parseInt(this.costList[i]['price'])
    }
    this.pricepp = Math.round(this.totalCost / this.participants * 100) / 100;
  }

  addElem(item, price) {
    if (item != "" && price != "") {
      this.costList.push({name: item, price: price});
      this.update()
    }

  }

  pay() {
    let dialogRef = this.dialog.open(ServiceSelectorComponent, {
      width: '600px',
    });
    dialogRef.componentInstance.module = this.module;
    dialogRef.componentInstance.id = this.parameters.sharedId;
    dialogRef.afterClosed().subscribe(() => {
      let body = new URLSearchParams();
      body.set('target', 'josuepd@gmail.com');
      body.set('amount', this.pricepp.toString());
      body.set('currency', 'EUR');
      body.set('service', this.selectorService.getSelectedService(this.module).name);
      body.set('userId', this.userS.getCurrentUser().name);
      // '{"code": "'+ code +'", "serviceName": "' + params.service + '"}'
      this.httpClient.post('http://' + this.config.apiEndpoint + "/back-1.0-SNAPSHOT/payment/pay", body.toString(),
        {
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).subscribe(
        data => {
          console.log(data);
          window.location.href = data.toString();
        },
        error => console.log(error)
      );
    });
  }

}
