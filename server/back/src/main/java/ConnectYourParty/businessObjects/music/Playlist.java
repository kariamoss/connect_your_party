package ConnectYourParty.businessObjects.music;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.database.DbMock;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Playlist {
    @Id
    private String id;

    @Transient
    private Event event = DbMock.event;

    @NotNull
    private String serviceHost;

    @Transient
    private User user = DbMock.user;

    public Playlist(String id, String serviceHost) {
        this.id = id;
        this.serviceHost = serviceHost;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Playlist &&
                this.getId().equals(((Playlist) obj).getId());
    }

    public String getId() {
        return id;
    }

    public String getServiceHost(){ return serviceHost; }
}
