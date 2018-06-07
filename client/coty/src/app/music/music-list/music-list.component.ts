import { Component, OnInit } from '@angular/core';
import {MusicModel} from "../../../model/music.model";

@Component({
  selector: 'app-music-list',
  templateUrl: './music-list.component.html',
  styleUrls: ['./music-list.component.css']
})
export class MusicListComponent implements OnInit {

  musicList: MusicModel[] = [];

  constructor() { }

  ngOnInit() {
    this.musicList.push(new MusicModel(1,"Salut 1", "Daronne de Lucas"))
    this.musicList.push(new MusicModel(2,"Salut 2", "Daronne de Lucas"))
    this.musicList.push(new MusicModel(3,"Salut 3", "Daronne de Lucas"))
  }

}
