import { Component, OnInit } from '@angular/core';
import {SelectorService} from "../services/selector.service";
import {AddPhotoService} from "../services/addPhoto.service";
import {NgForm} from '@angular/forms';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-add-photo',
  templateUrl: './add-photo.component.html',
  styleUrls: ['./add-photo.component.css']
})
export class AddPhotoComponent implements OnInit {

  module: string;
  headers;
  formData;

  constructor(private selectorService: SelectorService,
              private addPhotoService: AddPhotoService,
              private httpClient: HttpClient) { }

  ngOnInit() {
  }

  onSubmit() {
    this.httpClient.post('http://localhost:8080/back-1.0-SNAPSHOT/photo/addPhoto', this.formData,{headers: this.headers})
  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    let file: File = fileList[0];
    let formData:FormData = new FormData();
    formData.append('file', file);
    formData.append('name', file.name);
    this.formData = formData;
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    this.headers = headers;
  }

}
