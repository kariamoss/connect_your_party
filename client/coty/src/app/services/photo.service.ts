import {Inject, Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";
import {Photo} from "../../model/photo.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {APP_CONFIG, AppConfig} from "../app-config.module";

@Injectable()
export class PhotoService {

  photosSubject = new Subject<Photo[]>();

  constructor(private httpClient: HttpClient,
              @Inject(APP_CONFIG) public config: AppConfig,) {
  }

  getPhotos(eventId: number): void {
    const headers = new HttpHeaders();
    headers.append("Content-Type", 'application/json');
    const result = this.httpClient.get<Array<Photo>>('http://'+ this.config.apiEndpoint +'/back-1.0-SNAPSHOT/photo/getPhotoList', {headers: headers});
    result.subscribe(data => {
        this.photosSubject.next(data.slice());
      },
      error => console.log(error));
  }
}
