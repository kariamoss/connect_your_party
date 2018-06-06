import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-music-player',
  templateUrl: './music-player.component.html',
  styleUrls: ['./music-player.component.css']
})
export class MusicPlayerComponent implements OnInit {

  isPlaying;
  title;
  author;
  album;
  progressBarMode;
  currentSong;


  constructor() {
  }

  ngOnInit() {
    this.isPlaying = false;
    this.title = "Boulbi";
    this.author = "Booba";
    this.album = "Ouest Side";
    this.progressBarMode = "determinate"

    this.currentSong = new Audio("../../../assets/booba.mp3");
  }

  onPlay() {
    this.isPlaying = !this.isPlaying;
    if (this.isPlaying) {
      this.progressBarMode = "indeterminate";
      this.currentSong.play();
      this.currentSong.volume =  1;
    }
    else {
      this.progressBarMode = "determinate";
      this.currentSong.volume = 0;
    }

  }

  onNext(){
  }

  onPrevious() {
  }


}
