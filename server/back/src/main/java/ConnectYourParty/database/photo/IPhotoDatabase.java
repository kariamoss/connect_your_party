package ConnectYourParty.database.photo;

import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.photo.NoSuchPhotoException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IPhotoDatabase {

    List<Photo> getPhotoList();
    void addPhoto(Photo photo) throws AddPhotoErrorException;
    void removePhoto(Photo photo);
    Photo getPhotoFromPath(String path) throws NoSuchPhotoException;
    void clean();
}
