package ConnectYourParty.database;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.PhotoAlreadyExistException;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

public class DbMock {
    private List<Event> events = new ArrayList<>();
    public static Event event = new Event(0, "La_grande_soirée_costumée", 65, "12, route de Virey 70700 Charcenne");
    public static User user = new User("Milleret", "Jehan");
    private List<Photo> photos = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public Event getEvent() {
        return this.event;
    }

    public User getUser() {
        return this.user;
    }

    public List<Photo> getPhotosFromEvent(){
        return this.photos;
    }

    public void addPhoto(Event event, Photo photo) throws PhotoAlreadyExistException{
        if(photos.contains(photo)){
            throw new PhotoAlreadyExistException();
        }
        photos.add(photo);
        System.out.println("photo added");
    }

    public void clean(){
        event = new Event(0, "La_grande_soirée_costumée", 65, "12, route de Virey 70700 Charcenne");
        photos = new ArrayList<>();
    }

    public String getServiceFromPath(String photoPath) throws NoSuchPhotoException{
        for(Photo photo : photos){
            if(photo.getPhotoPath().equals(photoPath)){
                return photo.getServiceHost();
            }
        }
        throw new NoSuchPhotoException();
    }

    public Photo getPhotoFromPath(String path) throws NoSuchPhotoException{
        for(Photo photo : photos){
            if(photo.getPhotoPath().equals(path)){
                return photo;
            }
        }
        throw new NoSuchPhotoException();
    }

    public void removePhotoFromEvent(Event event , Photo photo){
        photos.remove(photo);
    }
}