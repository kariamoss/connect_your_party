
import {HttpClient} from "@angular/common/http";
import {UserModel} from "../../model/user.model";
import {APP_CONFIG, AppConfig} from "../app-config.module";
import {Inject, Injectable} from "@angular/core";

export class UserService{

  users: UserModel[] = [];

  constructor(private httpClient: HttpClient,
              @Inject(APP_CONFIG) public config: AppConfig,) {

    this.httpClient.get("http://localhost:8080/back-1.0-SNAPSHOT/user/list")
      .subscribe(data => {
        for (let i = 0; i < 10; i++) {
          this.users.push(new UserModel(data[i]['name'],data[i]['surname'],data[i]['status']))
        }
      })

  }

  getUsers(): UserModel[]{
    return this.users;
  }


  private currentUser: number = 0;

  getCurrentUser(){
    return this.users[this.currentUser];
  }

  setCurrentUser(num:number){
    this.currentUser = num;
  }

  getCurrentIndex(){
    return this.currentUser;
  }

  isCurrentUser(name: string){
    for(let i=0;i<this.users.length;i++){
      if(this.users[i].name == name && this.currentUser == i) return true
    }
    return false;
  }






}
