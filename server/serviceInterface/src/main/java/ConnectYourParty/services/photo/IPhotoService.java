package ConnectYourParty.services.photo;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.services.IService;

public interface IPhotoService extends IService {
    void addPhoto(byte[] photo, String path) throws AddPhotoErrorException;

    byte[] getPhoto(String path) throws RetrievePhotoErrorException;
}
