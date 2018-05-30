package ConnectYourParty.database.businessObjects;

public class Photo {
    private String photoPath;
    private String name;
    private User user;

    public Photo(String photoPath, String name, User user) {
        this.photoPath = photoPath;
        this.name = name;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getName() {
        return name;
    }
}
