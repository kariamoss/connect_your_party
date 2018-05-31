package ConnectYourParty.modulesLogic.interpreter;

import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.IPhoto;
import ConnectYourParty.modulesLogic.chooser.PhotoChooser;
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import org.apache.myfaces.util.FilenameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class PhotoInterpreter implements IPhoto {

    PhotoChooser photoChooser;

    public PhotoInterpreter(){
        photoChooser = new PhotoChooser();
    }

    @Override
    public void addPhoto(InputStream stream, String name,String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException {
        String path = DbMock.event.getId() + "/"
                + UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(name);

        Photo photo = new Photo(path, name, DbMock.user,serviceName);

        try {
            DbMock.addPhoto(DbMock.event, photo);
            path = "ConnectYourParty/" + path;
            photoChooser.addPhoto(stream, path, serviceName);
        } catch (Exception e){
            DbMock.removePhotoFromEvent(DbMock.event,photo);
        }

    }

    @Override
    public byte[] getPhoto(String path) throws RetrievePhotoErrorException,NoSuchServiceException, NoSuchPhotoException {
        return photoChooser.getPhoto(path);
    }

    @Override
    public void removePhoto(String path) throws CannotDeletePhotoException {
        photoChooser.removePhoto(path);
    }

    @Override
    public List<PhotoHolder> getPhotoList() {
        return photoChooser.getPhotoList();
    }
}
