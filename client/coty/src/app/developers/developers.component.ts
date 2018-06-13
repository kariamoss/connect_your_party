import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material";
import {SubmitDevComponent} from "./submit-dev/submit-dev.component";
import {ActivatedRoute, Router} from "@angular/router";
import { Location } from '@angular/common';

@Component({
  selector: 'app-developers',
  templateUrl: './developers.component.html',
  styleUrls: ['./developers.component.css']
})
export class DevelopersComponent implements OnInit {

  constructor(public dialog: MatDialog,
              private router: Router,
              private location: Location,
              private activatedRoute: ActivatedRoute,
              ) {
  }

  ngOnInit() {
    this.openTab('music');
  }

  openTab(id: string) {
    var i, tabcontent, tablinks;

    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    document.getElementById(id).style.display = "block";
  }


  submitService(id: string) {
    let dialogRef = this.dialog.open(SubmitDevComponent, {
      width: '600px',
    });
    dialogRef.componentInstance.module = id;
  }

  openJavadoc() {
    //TODO for my bro JEHAN
  }

  downloadInterface(){
    //TODO for my bro JEHAN
  }

}
