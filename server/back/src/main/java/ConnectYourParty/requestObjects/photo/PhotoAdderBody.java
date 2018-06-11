package ConnectYourParty.requestObjects.photo;

import java.io.InputStream;

public class PhotoAdderBody {
    private String name;
    private String service;
    private InputStream input;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public boolean check(){
        return name != null &&
                service != null &&
                userId != null &&
                input != null;
    }
}
