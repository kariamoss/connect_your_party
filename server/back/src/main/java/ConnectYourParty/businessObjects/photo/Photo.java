package ConnectYourParty.businessObjects.photo;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.database.DbMock;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Photo implements Serializable {

    @Id
    private String path;

    @NotNull
    private String name;

    @Transient
    private Event event = DbMock.event;

    @NotNull
    private String serviceHost;

    @Transient
    private User user = DbMock.user;

    public Photo(){

    }

    public Photo(String name, String serviceHost) {
        this.name = name;
        this.path = this.event.getId()+"/"+name;
        this.serviceHost = serviceHost;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public User getUser() {
        return user;
    }

    public String getPhotoPath() {
        return this.path;
    }

    public String getPrivatePhotoPath(){
        return "/ConnectYourParty/"+getPhotoPath();
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return this.getPhotoPath().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(Photo.class) &&
                this.getPhotoPath().equals(((Photo) obj).getPhotoPath());
    }
}
