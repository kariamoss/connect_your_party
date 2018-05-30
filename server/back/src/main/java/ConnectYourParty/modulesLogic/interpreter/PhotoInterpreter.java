package ConnectYourParty.modulesLogic.interpreter;

import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.IPhoto;
import ConnectYourParty.modulesLogic.chooser.PhotoChooser;
import ConnectYourParty.requestObjects.photo.PhotoHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PhotoInterpreter implements IPhoto {

    PhotoChooser photoChooser;

    public PhotoInterpreter(){
        photoChooser = new PhotoChooser();
    }

    @Override
    public void addPhoto(InputStream stream, String path,String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException {
        Photo photo = new Photo(path,path, DbMock.user,serviceName);

        DbMock.addPhoto(photo);

        photoChooser.addPhoto(stream, path, serviceName);
    }

    @Override
    public byte[] getPhoto(String path) throws RetrievePhotoErrorException {
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
