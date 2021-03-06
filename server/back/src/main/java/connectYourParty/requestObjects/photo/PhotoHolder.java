package connectYourParty.requestObjects.photo;

import connectYourParty.businessObjects.photo.Photo;
import connectYourParty.businessObjects.User;

public class PhotoHolder {
    public String photoPath;
    public String name;
    public String user;

    public PhotoHolder(String photoPath, String name, User user, String serviceHost) {
        this.photoPath = photoPath;
        this.name = name;
        this.user = user.getSurname() + " " + user.getName();
    }
    public PhotoHolder(Photo photo) {
        this.photoPath = photo.getPhotoPath();
        this.name = photo.getName();
        this.user = "Jehan";
    }
}