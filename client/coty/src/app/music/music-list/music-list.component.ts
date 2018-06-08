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
    this.musicList.push(new MusicModel(1,"Alors on danse","Rachel Matteo"));
    this.musicList.push(new MusicModel(2,"Prenez moi","Rachel Matteo"));
    this.musicList.push(new MusicModel(3,"Jusqu'au bout","Rachel Matteo"));
  }

  getList(){
    let url = "http://localhost:8080/back-1.0-SNAPSHOT/music/listMusic/Spotify";
    this.httpClient.get(url)
      .subscribe(data => {
          for (let i = 0; i < 10; i++) {
            let m = new MusicModel(data[i]['id'],data[i]['title'],data[i]['artist']);
            this.musicList.push(m);
          }
        }
      );
  }

}
