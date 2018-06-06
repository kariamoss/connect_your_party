package ConnectYourParty.services;

import java.net.URL;

public interface IService {
    String getServiceName();
    URL getServiceIcon();
    URL getOAuth();
    URL getOAuthToken();
    String getAppKey();
    String getAppSecret();
}
