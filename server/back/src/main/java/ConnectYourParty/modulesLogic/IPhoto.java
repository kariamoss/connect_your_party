package ConnectYourParty.modulesLogic;

import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;

import java.io.IOException;
import java.io.InputStream;

public interface IPhoto {
    void addPhoto(InputStream stream, String path,String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException;
    void getPhotos(String path) throws RetrievePhotoErrorException;
    void removePhoto(String path) throws CannotDeletePhotoException;
}
