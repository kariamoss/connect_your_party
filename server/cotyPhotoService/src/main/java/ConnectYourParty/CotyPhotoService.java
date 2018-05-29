package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.services.photo.IPhotoService;

import java.net.URL;

public class CotyPhotoService implements IPhotoService {
    @Override
    public void addPhoto(byte[] photo, String path) throws AddPhotoErrorException {
        Photos.addPicture(photo, path);
    }

    @Override
    public byte[] getPhoto(String path) throws RetrievePhotoErrorException {
        return Photos.retrievePhoto(path);
    }

    @Override
    public String getServiceName() {
        return "Album photo de Connect Your Party";
    }

    @Override
    public URL getServiceIcon() {
        return null;
    }
}
