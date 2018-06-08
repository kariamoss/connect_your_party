import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Photo} from "../../../model/photo.model";
import {MusicModel} from "../../../model/music.model";

@Component({
  selector: 'app-music-search',
  templateUrl: './music-search.component.html',
  styleUrls: ['./music-search.component.css']
})
export class MusicSearchComponent implements OnInit {

  searchResults: MusicModel[] = [];

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.searchMusic("hello");
  }

  searchMusic(input) {
    this.searchResults = [];
    let url = "http://localhost:8080/back-1.0-SNAPSHOT/music/searchMusic/Spotify/" + encodeURIComponent(input);
    this.httpClient.get(url)
      .subscribe(data => {
          for (let i = 0; i < 10; i++) {
            let m = new MusicModel(data[i]['id'], data[i]['title'], data[i]['artist']);
            this.searchResults.push(m);
          }
        }
      );

  }

  addToPlaylist(id) {
    let url = "http://localhost:8080/back-1.0-SNAPSHOT/music/addMusic";
    let idSong = id;
    let service = "Spotify";
    const body = {
      idSong, service
    };
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.httpClient.post(url, body, {headers: headers});
  }

}
