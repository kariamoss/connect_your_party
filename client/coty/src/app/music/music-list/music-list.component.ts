import { Component, OnInit } from '@angular/core';
import {MusicModel} from "../../../model/music.model";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-music-list',
  templateUrl: './music-list.component.html',
  styleUrls: ['./music-list.component.css']
})
export class MusicListComponent implements OnInit {

  musicList: MusicModel[] = [];

  constructor(public httpClient: HttpClient){ }

  ngOnInit(){
    this.getList();
  }

  getList(){
    let url = "http://localhost:8080/back-1.0-SNAPSHOT/music/listMusic/Spotify";
    this.httpClient.get(url)
      .subscribe(songs => {
          for (let song in songs) {
            let m = new MusicModel(song['id'],song['title'],song['artist']);
            this.musicList.push(m);
          }
        }
      );
  }

}
