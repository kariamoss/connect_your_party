export class Service {
  constructor(
    public name: string,
    public icon: string,
    public oAuthURL: string,
    public oAuthTokenURL: string,
    public client_id: string,
    public client_secret: string,
  ){}
}
