package ConnectYourParty.services;

import java.net.URL;

public interface IService {
    /**
     * Retrieve the name of the service
     * @return String of the service name
     */
    String getServiceName();

    /**
     * Retrieve the url of the service icon
     * Only icons will be used
     * @return URL containing the service icon
     */
    URL getServiceIcon();

    /**
     * Retrieve the URL where the service
     * need to make a GET request to login and retrieve
     * a user token
     * Leave blank if there is no OAuth login on your service
     * @return URL where the service need to login
     */
    URL getOAuth();

    /**
     * When the OAuth token is retrieved
     * Call this method to set it
     * Never call if there is no OAuth login on your service
     * @param oAuthToken the token
     */
    void setOAuthToken(String oAuthToken);

    /**
     * Retrieve the app key of the service if the service needs
     * to connect with OAuth
     * Leave blank if there is no OAuth login on your service
     * @return String containing the app key of the service
     */
    String getAppKey();
}
