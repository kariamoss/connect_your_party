package ConnectYourParty.services.photo;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.services.IService;

import java.util.Optional;

public interface IPhotoService extends IService {
    void addPhoto(byte[] photo, String path, Optional<TokenService> token) throws AddPhotoErrorException;

    byte[] getPhoto(String path, Optional<TokenService> token) throws RetrievePhotoErrorException;

    void removePhoto(String path, Optional<TokenService> token) throws CannotDeletePhotoException;
}
