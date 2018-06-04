package ConnectYourParty.businessObjects;

public class Photo {
    private String eventId;
    private String name;
    private User user;
    private String serviceHost;

    public Photo(String eventId, String name, User user, String serviceHost) {
        this.eventId = eventId;
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
        return eventId+"/"+name;
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
