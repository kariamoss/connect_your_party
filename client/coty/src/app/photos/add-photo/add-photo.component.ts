import {Component, OnInit} from '@angular/core';
import {SelectorService} from "../../services/selector.service";
import {NgForm} from '@angular/forms';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {isNull} from "util";
import {MatDialogRef} from "@angular/material";
import {PhotoService} from "../../services/photo.service";

@Component({
  selector: 'app-add-photo',
  templateUrl: './add-photo.component.html',
  styleUrls: ['./add-photo.component.css'],
})
export class AddPhotoComponent implements OnInit {

  module: string;
  headers: HttpHeaders;
  formData: FormData;
  uploading: boolean = false;

  constructor(private selectorService: SelectorService,
              private httpClient: HttpClient,
              private dialogRef: MatDialogRef<AddPhotoComponent>,
              private photoService: PhotoService) {
  }

  ngOnInit() {
  }

  onSubmit() {
    if (isNull(this.selectorService.getSelectedService(this.module))) {
      alert("Veuillez sélectionner un service avant");
      return;
    }
    this.formData.append('service', this.selectorService.getSelectedService(this.module).name);
    this.formData.append('eventId', "1");
    this.uploading = true;
    this.httpClient.post('http://localhost:8080/back-1.0-SNAPSHOT/photo/addPhoto', this.formData, {headers: this.headers})
      .subscribe(
      data => {
        this.dialogRef.close();
        this.photoService.getPhotos(1);
      },
      error => console.log(error),
      () => this.uploading = false,
    );
  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    let file: File = fileList[0];
    let formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('name', file.name);
    this.formData = formData;
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    this.headers = headers;
  }

}
