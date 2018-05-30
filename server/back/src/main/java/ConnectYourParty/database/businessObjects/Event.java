package ConnectYourParty.database.businessObjects;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private String title;
    private int nbPeople;
    private String location;
    private List<Photo> photos;

    public Event(int id, String title, int nbPeople, String location) {
        this.id = id;
        this.title = title;
        this.nbPeople = nbPeople;
        this.location = location;
        this.photos = new ArrayList<>();
    }

    public void addPhoto(Photo photo){
        this.photos.add(photo);
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getNbPeople() {
        return nbPeople;
    }

    public String getLocation() {
        return location;
    }
}
