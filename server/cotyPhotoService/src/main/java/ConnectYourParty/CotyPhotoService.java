package ConnectYourParty;

import ConnectYourParty.services.photo.IPhotoService;

import java.net.URL;

public class CotyPhotoService implements IPhotoService {
    @Override
    public String addPhoto(byte[] photo, String path) {
        return null;
    }

    @Override
    public byte[] getPhoto(String photo) {
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
