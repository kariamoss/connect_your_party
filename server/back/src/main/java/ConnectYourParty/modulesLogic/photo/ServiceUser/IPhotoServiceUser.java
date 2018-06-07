package ConnectYourParty.modulesLogic.photo.ServiceUser;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.services.photo.IPhotoService;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface IPhotoServiceUser {

    List<IPhotoService> getServiceList();
    void removePhoto(Photo photo, Optional<Token> token) throws CannotDeletePhotoException, NoSuchServiceException;
    byte[] getPhoto(Photo photo, Optional<Token> token) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException;
    void addPhoto(Photo photo, byte[] bin, Optional<Token> token) throws AddPhotoErrorException, NoSuchServiceException;

}
