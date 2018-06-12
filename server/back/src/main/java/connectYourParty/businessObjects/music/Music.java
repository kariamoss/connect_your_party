package connectYourParty.businessObjects.music;

import connectYourParty.businessObjects.Event;
import connectYourParty.businessObjects.User;
import connectYourParty.database.DbMock;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Music {
    @Id
    private String id;

    @Transient
    private Event event = DbMock.event;

    @NotNull
    private String serviceHost;

    @Transient
    private User user = DbMock.user;

    private String title;

    private String artist;

    public Music(){ }

    public Music(String id, String serviceHost, String title, String artist) {
        this.id = id;
        this.serviceHost = serviceHost;
        this.title = title;
        this.artist = artist;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(Music.class) &&
                this.getId().equals(((Music) obj).getId());
    }

    public String getId() {
        return id;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
