package ConnectYourParty.database;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.businessObjects.User;

import java.util.ArrayList;
import java.util.List;

public class dbMock {
    List<Event> events;

    public dbMock(){
        events = new ArrayList<>();
        Event event = new Event(0, "La grande soirée costumée", 65, "12, route de Virey 70700 Charcenne");
        User user = new User("Milleret", "Jehan");
        Photo photo = new Photo("image.jpg", "la soirée", user);
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Photo> getPhotosFromEvent(Event event){
        if(events.contains(event)){
            return events.get(events.indexOf(event)).getPhotos();
        }
        return null;
    }
}
