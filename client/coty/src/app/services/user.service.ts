import {HttpClient} from "@angular/common/http";
import {UserModel} from "../../model/user.model";
import {APP_CONFIG, AppConfig} from "../app-config.module";
import {Inject} from "@angular/core";
import {SelectorService} from "./selector.service";

export class UserService {

  users: UserModel[] = [];
  private currentUser: number;

  constructor(private httpClient: HttpClient,
              @Inject(APP_CONFIG) public config: AppConfig,
              private selectorService: SelectorService,) {

    this.httpClient.get("http://localhost:8080/back-1.0-SNAPSHOT/user/list")
      .subscribe(data => {
        for (let i = 0; i < 4; i++) {
          this.users.push(new UserModel(data[i]['name'], data[i]['surname'], data[i]['status']))
        }

        if (!sessionStorage.getItem('user')) {
          this.currentUser = 0;
          sessionStorage.setItem('user', this.users[0].name);
        } else {
          this.currentUser = this.getIdByName(sessionStorage.getItem('user'));
        }

      });




  }


  getIdByName(name: string): number {
    for (let i = 0; i < this.users.length; i++) {
      console.log(this.users[i].name+" / "+name);
      if (this.users[i].name === name) return i;
    }
  }

  getUsers(): UserModel[] {
    return this.users;
  }


  getCurrentUser() {
    return this.users[this.currentUser];
  }

  setCurrentUser(num: number) {
    this.currentUser = num;
    sessionStorage.setItem('user', this.users[num].name);
    this.selectorService.clearSelectedService();
  }

  getCurrentIndex() {
    return this.currentUser;
  }

  currentUserIsAdmin(){
    if(this.users.length >0){
      return this.users[this.currentUser].role == 'admin';
    } else return false;

  }




}
