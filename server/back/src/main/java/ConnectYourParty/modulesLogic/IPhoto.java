package ConnectYourParty.modulesLogic;

import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.requestObjects.photo.PhotoHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IPhoto {
    void addPhoto(InputStream stream, String path,String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException;
    byte[] getPhoto(String path) throws RetrievePhotoErrorException;
    void removePhoto(String path) throws CannotDeletePhotoException;

    List<PhotoHolder> getPhotoList();
}
