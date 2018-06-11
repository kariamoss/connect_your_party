package ConnectYourParty.modulesLogic.photo.interpreter;

import ConnectYourParty.exception.NoSuchUserException;
import ConnectYourParty.exception.photo.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.photo.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import ConnectYourParty.requestObjects.photo.PhotoServiceHolder;

import javax.ejb.Local;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Local
public interface IPhotoInterpreter {

    void addPhoto(InputStream stream, String path, String serviceName, String userId) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException, NoSuchUserException;
    byte[] getPhoto(String path) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException;
    void removePhoto(String path) throws RetrievePhotoErrorException,NoSuchServiceException, NoSuchPhotoException,CannotDeletePhotoException;

    List<PhotoHolder> getPhotoList();
    List<PhotoServiceHolder> getServices();
}
