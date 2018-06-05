import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {APP_CONFIG, AppConfig} from "../../app-config.module";
import {MatDialogRef} from "@angular/material";
import {AddPhotoComponent} from "../../photos/add-photo/add-photo.component";

@Component({
  selector: 'app-submit-dev',
  templateUrl: './submit-dev.component.html',
  styleUrls: ['./submit-dev.component.css']
})
export class SubmitDevComponent implements OnInit {

  headers: HttpHeaders;
  formData: FormData;
  uploading: boolean = false;
  module: string;
  serviceName: string;

  constructor(private httpClient: HttpClient,
              private dialogRef: MatDialogRef<SubmitDevComponent>,
              @Inject(APP_CONFIG) private config: AppConfig,) {
  }

  ngOnInit() {

  }

  onSubmit() {
    /*
    this.formData.append('module', this.module);
    this.formData.append('name', this.serviceFileName);
    this.uploading = true;

    this.httpClient.post('http://' + this.config.apiEndpoint + '/back-1.0-SNAPSHOT/service/addService', this.formData, {headers: this.headers})
      .subscribe(
        data => {

        },
        error => console.log(error),
        () => this.uploading = false,
      );
     */
    this.uploading=true;
    console.log('service submitted to lucas');
    console.log('name : ' + this.serviceName);
    console.log('module : ' + this.module);

    document.getElementById('submitForm').hidden = true;
    this.dialogRef.close();

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
