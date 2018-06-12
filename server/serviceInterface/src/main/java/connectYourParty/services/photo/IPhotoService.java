package connectYourParty.services.photo;

import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.exceptions.photo.RetrievePhotoErrorException;
import connectYourParty.objects.TokenService;
import connectYourParty.services.IService;

import java.util.Optional;

public interface IPhotoService extends IService {
    void addPhoto(byte[] photo, String path, Optional<TokenService> token) throws AddPhotoErrorException;

    byte[] getPhoto(String path, Optional<TokenService> token) throws RetrievePhotoErrorException;

    void removePhoto(String path, Optional<TokenService> token) throws CannotDeletePhotoException;
}
