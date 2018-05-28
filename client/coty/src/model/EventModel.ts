

export class EventModel{
  constructor(){
  }

  getTitle(){
    return "Title";
  }

  getDescription(){
    return "Description";
  }

  getNbParticipants(){
    return Math.floor(Math.random() * 100);
  }

  getLocation(){
    return "chez la daronne à lucas";
  }

  getColorTheme(){
    return "lightblue";
  }
}
