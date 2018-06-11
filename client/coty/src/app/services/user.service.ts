import {UserModel} from "../../model/user.model";

export class UserService{
  private users: UserModel[] = [
    new UserModel("jean","paul","admin"),
    new UserModel("hugo","philip","default"),
    new UserModel("lucas","lepd","default")
  ];

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

  getUsers(): UserModel[]{
    return this.users;
  }



}
