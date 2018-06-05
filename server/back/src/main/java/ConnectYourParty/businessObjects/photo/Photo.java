package ConnectYourParty.businessObjects.photo;

import ConnectYourParty.businessObjects.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Photo implements Serializable {

    @Id
    private String path;

    @NotNull
    private String name;

    @NotNull
    private String eventId;

    @NotNull
    private String serviceHost;

    public Photo(){

    }

    public Photo(String eventId, String name, String serviceHost) {
        this.name = name;
        this.eventId = eventId;
        this.path = eventId+"/"+name;
        this.serviceHost = serviceHost;
    }

    public String getServiceHost() {
        return serviceHost;
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
