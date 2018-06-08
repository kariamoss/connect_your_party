package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.services.photo.IPhotoService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class CotyPhotoService implements IPhotoService {
    @Override
    public void addPhoto(byte[] photo, String path, Optional<TokenService> token) throws AddPhotoErrorException {
        Photos.addPicture(photo, path);
    }

    @Override
    public byte[] getPhoto(String path, Optional<TokenService> token) throws RetrievePhotoErrorException {
        return Photos.retrievePhoto(path);
    }

    @Override
    public void removePhoto(String path, Optional<TokenService> token) throws CannotDeletePhotoException {
        Photos.removePhoto(path);
    }

    @Override
    public String getServiceName() {
        return "Album photo de Connect Your Party";
    }

    @Override
    public URL getServiceIcon() {
        try {
            return new URL("http://2.bp.blogspot.com/-V31y2Ef4Ad0/VZservQf70I/AAAAAAAAdu8/ErI--hbXwfE/s1600/OpenCamera1.png");
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
