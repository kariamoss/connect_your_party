package ConnectYourParty.modulesLogic.interpreter;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.IPhoto;
import ConnectYourParty.modulesLogic.chooser.PhotoChooser;

import java.io.IOException;
import java.io.InputStream;

public class PhotoInterpreter implements IPhoto {

    PhotoChooser photoChooser;

    public PhotoInterpreter(){
        photoChooser = new PhotoChooser();
    }

    @Override
    public void addPhoto(InputStream stream, String path,String serviceName) throws IOException, AddPhotoErrorException {
        photoChooser.addPhoto(stream, path);
    }

    @Override
    public void getPhotos(String path) throws RetrievePhotoErrorException {

    }

    @Override
    public void removePhoto(String path) throws CannotDeletePhotoException {
        photoChooser.removePhoto(path);
    }
}
