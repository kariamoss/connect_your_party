package ConnectYourParty.services;

import ConnectYourParty.objects.TokenService;

import java.net.URL;

public interface IServiceOAuth extends IService{
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
     * When the OAuth code is retrieved with {@link IServiceOAuth#getOAuthUrl()}
     * The service will need to update the token and send it back
     * Never call if there is no OAuth login on your service
     * @param oAuthCode the code retrieved before
     * @return token the valid token
     */
    TokenService getToken(String oAuthCode);

    TokenService refreshToken(String refreshToken);

    /**
     * Retrieve the app key of the service if the service needs
     * to connect with OAuth
     * Leave blank if there is no OAuth login on your service
     * @return String containing the app key of the service
     */
    String getAppKey();
}
