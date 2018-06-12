package connectYourParty.database.photo;

import connectYourParty.businessObjects.photo.Photo;
import connectYourParty.exception.photo.NoSuchPhotoException;
import connectYourParty.exceptions.photo.AddPhotoErrorException;

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
