
import {Subject} from "rxjs/index";
import {EventModel} from "../../model/event.model";
export class ShoppingListService{

  private list: string[] = ["du coca","des chips","des gâteaux"];
  listSubject = new Subject<string[]>();

  emitList() {
    this.listSubject.next(this.list.slice());
  }

  addElement(elem: string) {
    this.list.push(elem);
    this.emitList();
  }


  removeElement(elem: string){
    let index = this.list.indexOf(elem);
    if(index > -1)this.list.splice(index,1);
    console.log(this.list);
  }

}
