import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ParametersService} from "../services/parameters.service";

@Component({
  selector: 'app-music',
  templateUrl: './music.component.html',
  styleUrls: ['./music.component.css']
})
export class MusicComponent implements OnInit {

  id:number;

  constructor(private param : ParametersService) {
  }

  ngOnInit() {
  }

  connect() {
    let clientid = "6086d2f27df04485a1e378bdb127646c";
    let redirect = "http://localhost:4200/authentication/?service=Spotify";
    let scopes = "user-library-read user-library-modify playlist-read-private playlist-modify-public playlist-modify-private " +
      "playlist-read-collaborative user-read-private streaming";
    let state = this.param.sharedId+"/music";
    let url = 'https://accounts.spotify.com/authorize' +
      '?response_type=code' +
      '&client_id=' + clientid + '&scope=' + encodeURIComponent(scopes) +
      '&redirect_uri=' + encodeURIComponent(redirect)+"&state="+state;
    console.log(url);
    window.location.href = url;
  }
}
