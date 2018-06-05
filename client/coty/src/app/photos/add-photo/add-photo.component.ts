import {Component, Inject, OnInit} from '@angular/core';
import {SelectorService} from "../../services/selector.service";
import {NgForm} from '@angular/forms';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {isNull} from "util";
import {MatDialogRef} from "@angular/material";
import {PhotoService} from "../../services/photo.service";
import {APP_CONFIG, AppConfig} from "../../app-config.module";
import {TokenRetrieverService} from "../../services/tokenRetriever.service";

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
  id: number;

  constructor(private selectorService: SelectorService,
              private httpClient: HttpClient,
              private dialogRef: MatDialogRef<AddPhotoComponent>,
              private photoService: PhotoService,
              private tokenRetriever: TokenRetrieverService,
              @Inject(APP_CONFIG) private config: AppConfig,) {
  }

  ngOnInit() {
  }

  onSubmit() {
    const service = this.selectorService.getSelectedService(this.module);
    if (isNull(this.selectorService.getSelectedService(this.module))) {
      alert("Veuillez sélectionner un service avant");
      return;
    }
    this.formData.append('service', service.name);
    this.formData.append('eventId', this.id.toString());
    if (this.tokenRetriever.getToken(service)) {
      this.formData.append('access_token', this.tokenRetriever.getToken(service));
    }
    this.uploading = true;
    this.httpClient.post('http://' + this.config.apiEndpoint +'/back-1.0-SNAPSHOT/photo/addPhoto', this.formData, {headers: this.headers})
      .subscribe(
      data => {
        this.dialogRef.close();
        this.photoService.getPhotos(1);
      },
      error => console.log(error),
      () => this.uploading = false,
    );

    document.getElementById('mainForm').hidden = true;

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
