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
    URL getOAuthUrl();


    //TODO Pass those two methods in services and use updateToken
    URL getOAuthToken();
    String getAppSecret();

    /**
     * When the OAuth code is retrieved with {@link IService#getOAuthUrl()}
     * The service will need to update the token and send it back
     * Never call if there is no OAuth login on your service
     * @param oAuthToken the code retrieved before
     * @return token the valid token
     */
    //String updateToken(String oAuthToken);

    /**
     * Retrieve the app key of the service if the service needs
     * to connect with OAuth
     * Leave blank if there is no OAuth login on your service
     * @return String containing the app key of the service
     */
    String getAppKey();
}
