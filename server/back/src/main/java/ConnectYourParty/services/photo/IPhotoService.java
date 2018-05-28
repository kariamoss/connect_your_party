package ConnectYourParty.services.photo;

import ConnectYourParty.services.IService;

public interface IPhotoService extends IService {
    String addPhoto(String photo);

    String getPhotos(String photo);
}
