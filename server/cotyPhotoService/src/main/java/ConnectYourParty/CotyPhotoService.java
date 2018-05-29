package ConnectYourParty;

import ConnectYourParty.services.photo.IPhotoService;

import java.net.URL;

public class CotyPhotoService implements IPhotoService {
    @Override
    public void addPhoto(byte[] photo, String path) {

    }

    @Override
    public byte[] getPhoto(String path) {
        return new byte[0];
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
