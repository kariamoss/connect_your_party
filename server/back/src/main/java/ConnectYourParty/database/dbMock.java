package ConnectYourParty.database;

import ConnectYourParty.database.businessObjects.Event;
import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.database.businessObjects.User;

import java.util.ArrayList;
import java.util.List;

public class dbMock {
    static private List<Event> events = new ArrayList<>();
    static private Event event = new Event(0, "La grande soirée costumée", 65, "12, route de Virey 70700 Charcenne");
    static private User user = new User("Milleret", "Jehan");
    static private Photo photo = new Photo("image.jpg", "la soirée", user);

    public static List<Event> getEvents() {
        return events;
    }

    public static List<Photo> getPhotosFromEvent(Event event){
        if(events.contains(event)){
            return events.get(events.indexOf(event)).getPhotos();
        }
        return null;
    }
}
