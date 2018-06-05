package ConnectYourParty.database;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.Music;
import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.PhotoAlreadyExistException;

import javax.ejb.Singleton;
import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DbMock {
    private List<Event> events = new ArrayList<>();
    private Event event = new Event(0, "La_grande_soirée_costumée", 65, "12, route de Virey 70700 Charcenne");
    private User user = new User("Milleret", "Jehan");

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
        return this.event.getPhotos();
    }

    public void addPhoto(Event event, Photo photo) throws PhotoAlreadyExistException{
        if(event.getPhotos().contains(photo)){
            throw new PhotoAlreadyExistException();
        }
        event.addPhoto(photo);
        System.out.println("photo added");
    }

    public void clean(){
        event = new Event(0, "La_grande_soirée_costumée", 65, "12, route de Virey 70700 Charcenne");
    }

    public String getServiceFromPath(String photoPath) throws NoSuchPhotoException{
        for(Photo photo : event.getPhotos()){
            if(photo.getPhotoPath().equals(photoPath)){
                return photo.getServiceHost();
            }
        }
        throw new NoSuchPhotoException();
    }

    public Photo getPhotoFromPath(String path) throws NoSuchPhotoException{
        for(Photo photo : event.getPhotos()){
            if(photo.getPhotoPath().equals(path)){
                return photo;
            }
        }
        throw new NoSuchPhotoException();
    }

    public void removePhotoFromEvent(Event event , Photo photo){
        event.getPhotos().remove(photo);
    }


}