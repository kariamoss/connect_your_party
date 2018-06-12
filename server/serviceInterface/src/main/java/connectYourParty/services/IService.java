package connectYourParty.services;

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
}
