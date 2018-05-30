import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-photos-list',
  templateUrl: './photos-list.component.html',
  styleUrls: ['./photos-list.component.css']
})
export class PhotosListComponent implements OnInit {

  url: string;

  constructor() { }

  ngOnInit() {
    var app = angular.module('myApp', []);
    app.controller('myCtrl', function($scope, $http) {
      $http.get("localhost:8080/back-1.0-SNAPSHOT/photo/getPhotos")
        .then(function(response) {
          this.url = response.data;
        });
    });
  }

}
