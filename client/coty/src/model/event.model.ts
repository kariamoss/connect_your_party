export class EventModel{
  constructor(
    public id: number,
    public title: string,
    public desc: string,
    public people: number,
    public location: string,
    public date: string,
    public color: string
){}
}
