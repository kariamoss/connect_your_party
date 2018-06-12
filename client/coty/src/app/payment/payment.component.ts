import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {EventService} from "../services/events.service";
import {ParametersService} from "../services/parameters.service";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

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

  constructor(public userS: UserService, private eventS: EventService, private paramS: ParametersService) {
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
    if(item != "" && price != ""){
      this.costList.push({name: item, price: price});
      this.update()
    }

  }

}
