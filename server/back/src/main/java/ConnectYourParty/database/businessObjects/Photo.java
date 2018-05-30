package ConnectYourParty.database.businessObjects;

public class Photo {
    private String photoPath;
    private String name;
    private User user;
    private String service;

    public Photo(String photoPath, String name, User user,String service) {
        this.photoPath = photoPath;
        this.name = name;
        this.user = user;
        this.service = service;
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
