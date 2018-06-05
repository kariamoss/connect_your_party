package ConnectYourParty.businessObjects;

public class Event {
    private int id;
    private String title;
    private int nbPeople;
    private String location;

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
