package ConnectYourParty.businessObjects.photo;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.database.DbMock;

import javax.persistence.*;
import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

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

    @ManyToOne
    private Token accessToken;

    public Photo(){

    }

    public Photo(String name, String serviceHost) {
        this.name = name;
        this.path = this.event.getId()+"/"+name;
        this.serviceHost = serviceHost;
        this.accessToken = null;
    }

    public Photo(String name, String serviceHost, Token accessToken) {
        this.name = name;
        this.path = this.event.getId()+"/"+name;
        this.serviceHost = serviceHost;
        this.accessToken = accessToken;
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

    public Optional<Token> getAccessToken() {
        if(accessToken == null){
            return Optional.empty();
        }
        return Optional.of(accessToken);
    }
}
