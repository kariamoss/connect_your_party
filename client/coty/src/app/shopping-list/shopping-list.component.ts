import { Component, OnInit } from '@angular/core';
import {ShoppingListService} from "../services/shopping-list.service";
import {Subscription} from "rxjs/index";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html',
  styleUrls: ['./shopping-list.component.css']
})
export class ShoppingListComponent implements OnInit {

  list: string[];
  listSubscription: Subscription;

  constructor(private shoppingService : ShoppingListService) { }

  ngOnInit() {
    this.listSubscription = this.shoppingService.listSubject.subscribe(
      (list: string[]) => {
        this.list = list;
      }
    );
    this.shoppingService.emitList();
  }

  addElem(item: string){
    this.shoppingService.addElement(item)
  }

  removeElem(item: string){
    this.shoppingService.removeElement(item);
    document.getElementById(item).remove();

  }

}
