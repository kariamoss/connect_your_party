package ConnectYourParty.modulesLogic;

import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IPhoto {
    void addPhoto(InputStream stream, String path,String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException;
    byte[] getPhoto(String path) throws RetrievePhotoErrorException;
    void removePhoto(String path) throws CannotDeletePhotoException;

    List<Photo> getPhotoList();
}
