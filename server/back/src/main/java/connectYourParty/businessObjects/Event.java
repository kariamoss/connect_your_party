package connectYourParty.businessObjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String title;

    @NotNull
    private int nbPeople;

    @NotNull
    private String location;

    public Event(){

    }

    public Event(int id, String title, int nbPeople, String location) {
        this.id = id;
        this.title = title;
        this.nbPeople = nbPeople;
        this.location = location;
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
