package ConnectYourParty.requestObjects.photo;

import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.database.businessObjects.User;

public class PhotoHolder {
    public String photoPath;
    public String name;
    public String user;

    public PhotoHolder(String photoPath, String name, User user) {
        this.photoPath = photoPath;
        this.name = name;
        this.user = user.getSurname() + " " + user.getName();
    }
    public PhotoHolder(Photo photo) {
        this.photoPath = photo.getPhotoPath();
        this.name = photo.getName();
        this.user = photo.getUser().getSurname() + " " + photo.getUser().getName();
    }
}
