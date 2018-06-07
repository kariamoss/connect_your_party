import {Service} from "./service.model";

export class TokenHolder{
  constructor(
    public access_token: string,
    public service: Service,
    public refresh_token: string,
  ){ }
}
