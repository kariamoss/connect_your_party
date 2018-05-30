package ConnectYourParty.database.businessObjects;

public class Photo {
    private String photoPath;
    private String name;
    private User user;
    private String serviceHost;

    public Photo(String photoPath, String name, User user, String serviceHost) {
        this.photoPath = photoPath;
        this.name = name;
        this.user = user;
        this.serviceHost = serviceHost;
    }

    public String getServiceHost() {
        return serviceHost;
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
