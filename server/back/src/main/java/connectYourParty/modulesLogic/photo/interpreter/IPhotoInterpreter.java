package connectYourParty.modulesLogic.photo.interpreter;

import connectYourParty.exception.NoSuchUserException;
import connectYourParty.exception.photo.NoSuchPhotoException;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.photo.PhotoAlreadyExistException;
import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.exceptions.photo.RetrievePhotoErrorException;
import connectYourParty.requestObjects.photo.PhotoHolder;
import connectYourParty.requestObjects.photo.PhotoServiceHolder;

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
