import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-music',
  templateUrl: './music.component.html',
  styleUrls: ['./music.component.css']
})
export class MusicComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  connect() {

    var clientid = "6086d2f27df04485a1e378bdb127646c";
    var redirect = "http://localhost:4200/authentication/?service=spotify";
    var scopes = "user-library-read user-library-modify playlist-read-private playlist-modify-public playlist-modify-private " +
      "playlist-read-collaborative user-read-private streaming";
    var url = 'https://accounts.spotify.com/authorize' +
    '?response_type=code' +
    '&client_id=' + clientid + '&scope='+encodeURIComponent(scopes)+
      '&redirect_uri='+encodeURIComponent(redirect);
    console.log(url);
    window.location.href = url;
  }

}
