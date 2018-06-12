import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {UserModel} from "../../model/user.model";
import {MatDialogRef} from "@angular/material";
import {AddPhotoComponent} from "../photos/add-photo/add-photo.component";
import {EventComponent} from "../event/event.component";

@Component({
  selector: 'app-user-switch',
  templateUrl: './user-switch.component.html',
  styleUrls: ['./user-switch.component.css']
})
export class UserSwitchComponent implements OnInit {

  users: UserModel[];

  constructor(private userService: UserService,private dialogRef: MatDialogRef<EventComponent>) { }

  ngOnInit() {
    this.users = this.userService.getUsers();
  }


  isCurrentUser(id: number){
    return this.userService.getCurrentIndex() == id;
  }

  setAsCurrent(id) {
    this.userService.setCurrentUser(id)
  }

  closeDialog(){
    this.dialogRef.close();
  }

}
