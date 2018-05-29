package ConnectYourParty.services.photo;

import ConnectYourParty.services.IService;

public interface IPhotoService extends IService {
    String addPhoto(byte[] photo, String path);

    byte[] getPhoto(String photo);
}
