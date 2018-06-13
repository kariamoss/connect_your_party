package connectYourParty.modulesLogic.photo.ServiceUser;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.photo.Photo;
import connectYourParty.exception.photo.NoSuchPhotoException;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.exceptions.photo.RetrievePhotoErrorException;
import connectYourParty.requestObjects.photo.PhotoServiceHolder;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface IPhotoServiceUser {

    List<PhotoServiceHolder> getServiceList();
    void removePhoto(Photo photo, Optional<Token> token) throws CannotDeletePhotoException, NoSuchServiceException;
    byte[] getPhoto(Photo photo, Optional<Token> token) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException;
    void addPhoto(Photo photo, byte[] bin, Optional<Token> token) throws AddPhotoErrorException, NoSuchServiceException;

}
