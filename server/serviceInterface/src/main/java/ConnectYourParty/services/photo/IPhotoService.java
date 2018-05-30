package ConnectYourParty.services.photo;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.services.IService;

import java.util.Collection;

public interface IPhotoService extends IService {
    void addPhoto(byte[] photo, String path) throws AddPhotoErrorException;

    byte[] getPhoto(String path) throws RetrievePhotoErrorException;

    void removePhoto(String path) throws CannotDeletePhotoException;
}
