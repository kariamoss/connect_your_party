package ConnectYourParty.webInterface.services.photo;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class DriveService implements IPhotoService {
    @Override
    public String addPhoto(String photo) {
        return null;
    }

    @Override
    public String getPhotos(String photo) {
        return null;
    }

    @Override
    public String getServiceName() {
        return "Google Drive";
    }

    @Override
    public URL getServiceIcon() {
        URL url = null;
        try {
            url = new URL("http://icons.iconarchive.com/icons/uiconstock/socialmedia/256/Google-Drive-icon.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
