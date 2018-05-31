import {Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";
import {Photo} from "../../model/photo.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class PhotoService {

  photosSubject = new Subject<Photo[]>();

  private photos: Array<Photo> = [];

  constructor(private httpClient: HttpClient) {
  }

  emitPhotoSubject() {
    this.photosSubject.next(this.photos.slice());
  }

  getPhotos(): void {
    const headers = new HttpHeaders();
    headers.append("Content-Type", 'application/json');
    const result = this.httpClient.get<Array<Photo>>('http://localhost:8080/back-1.0-SNAPSHOT/photo/getPhotoList', {headers: headers});
    result.subscribe(data => {
        while (data.length > 0) {
          const elt = data.pop();
          const index = this.photos.map(x => x.name).indexOf(elt.name);
          if (index === -1) {
            this.photos.push(elt);
          }
        }
        this.emitPhotoSubject();
      },
      error => console.log(error));
  }
}
