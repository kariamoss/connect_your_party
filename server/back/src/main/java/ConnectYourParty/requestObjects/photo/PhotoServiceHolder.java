package ConnectYourParty.requestObjects.photo;

public class PhotoServiceHolder {
    public String name;
    public String icon;
    public String oAuthURL;
    public String oAuthTokenURL;
    public String client_id;
    public String client_secret;
    public int id;

    public PhotoServiceHolder(String name, String icon, String oAuthURL, String oAuthTokenURL, String client_id, String client_secret, int id) {
        this.name = name;
        this.icon = icon;
        this.oAuthURL = oAuthURL;
        this.oAuthTokenURL = oAuthTokenURL;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.id = id;
    }

    public PhotoServiceHolder(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
}
