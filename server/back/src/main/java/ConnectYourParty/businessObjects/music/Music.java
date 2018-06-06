package ConnectYourParty.businessObjects.music;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.database.DbMock;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Music {
    @Id
    private String path;

    @Transient
    private Event event = DbMock.event;

    @NotNull
    private String serviceHost;

    @Transient
    private User user = DbMock.user;

    private String title;

    private String artist;

    public Music(){ }

    public Music(String path, String serviceHost,String title, String artist) {
        this.path = path;
        this.serviceHost = serviceHost;
        this.title = title;
        this.artist = artist;
    }
}
