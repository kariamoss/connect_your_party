package ConnectYourParty.modulesLogic.interpreter;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.IPhoto;

import java.io.IOException;
import java.io.InputStream;

public class PhotoInterpreter implements IPhoto {


    @Override
    public void addPhoto(InputStream stream, String path) throws IOException, AddPhotoErrorException {

    }

    @Override
    public void getPhotos(String path) throws RetrievePhotoErrorException {

    }

    @Override
    public void removePhoto(String path) throws CannotDeletePhotoException {

    }
}
