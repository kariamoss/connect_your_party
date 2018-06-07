package ConnectYourParty.modulesLogic.photo.ServiceUser;

import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.requestObjects.photo.PhotoServiceHolder;
import ConnectYourParty.services.photo.IPhotoService;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IPhotoServiceUser {

    List<PhotoServiceHolder> getServiceList();
    void removePhoto(Photo photo) throws CannotDeletePhotoException, NoSuchServiceException;
    byte[] getPhoto(Photo photo) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException;
    void addPhoto(Photo photo, byte[] bin) throws AddPhotoErrorException, NoSuchServiceException;

}
