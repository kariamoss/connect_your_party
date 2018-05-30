package ConnectYourParty.database;

import ConnectYourParty.database.businessObjects.Event;
import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.database.businessObjects.User;
import ConnectYourParty.exception.PhotoAlreadyExistException;

import java.util.ArrayList;
import java.util.List;

public class DbMock {
    static private List<Event> events = new ArrayList<>();
    static private Event event = new Event(0, "La grande soirée costumée", 65, "12, route de Virey 70700 Charcenne");
    static public User user = new User("Milleret", "Jehan");

    public static List<Event> getEvents() {
        return events;
    }

    public static List<Photo> getPhotosFromEvent(){
        return event.getPhotos();
    }

    public static void addPhoto(Photo photo) throws PhotoAlreadyExistException{
        for(Photo res : event.getPhotos()){
            if(photo.getName().equals(res.getName())){
                throw new PhotoAlreadyExistException();
            }
        }
        event.addPhoto(photo);
    }

    public static void clean(){
        event = new Event(0, "La grande soirée costumée", 65, "12, route de Virey 70700 Charcenne");
    }
}