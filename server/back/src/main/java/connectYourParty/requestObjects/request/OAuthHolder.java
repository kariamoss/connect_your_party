package connectYourParty.requestObjects.request;

public class OAuthHolder {
    private String client_id;
    private String OAuthURL;

    public OAuthHolder(String client_id, String OAuthURL) {
        this.client_id = client_id;
        this.OAuthURL = OAuthURL;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getOAuthURL() {
        return OAuthURL;
    }
}
