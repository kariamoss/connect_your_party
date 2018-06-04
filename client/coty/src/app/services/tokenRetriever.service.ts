import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {TokenHolder} from "../../model/tokenHolder.model";
import {Service} from "../../model/service.model";
import {isUndefined} from "util";


@Injectable()
export class TokenRetrieverService {

  private tokens: Array<TokenHolder> = [];

  constructor(private httpClient: HttpClient,) {

  }

  retrieveToken(service: Service, code: string, id: number) {
        let formData: FormData = new FormData();
        formData.append('code', code);
        formData.append('grant_type', 'authorization_code');
        formData.append('client_id', service.client_id);
        formData.append('client_secret', service.client_secret);
        formData.append('redirect_uri', 'http://localhost:4200/events/' + id + '/photos?service=' + service.name);
        let headers = new HttpHeaders();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
        this.httpClient.post('https://' + service.oAuthTokenURL, formData, {headers: headers})
          .subscribe(
            data => {
              this.setToken(service, data['access_token']);
              console.log('Retrieved token for ' + service.name + ' : ' + this.getToken(service))
            },
            error => console.log(error)
          );
  }

  setToken(service: Service, access_token: string): void {
    const index = this.tokens.map(x => x.serviceName).indexOf(service.name);
    if (index === -1) {
      this.tokens.push(new TokenHolder(access_token, service.name));
    } else {
      this.tokens[0].access_token = access_token;
    }
  }

  getToken(service: Service) {
    const tmp = this.tokens.find(
      (tokenHolder) => {
        return tokenHolder.serviceName === service.name;
      }
    );
    return tmp.access_token;
  }
}
