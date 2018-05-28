package ConnectYourParty.webInterface.services.photo;

import ConnectYourParty.webInterface.services.IService;

import javax.xml.ws.Response;
import java.net.URI;

public interface IPhotoService extends IService {
    String addPhoto(String photo);

    String getPhotos(String photo);
}
