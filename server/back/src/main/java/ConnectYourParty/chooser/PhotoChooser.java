package ConnectYourParty.chooser;

import ConnectYourParty.DropboxService;
import ConnectYourParty.services.photo.IPhotoService;

import java.util.ArrayList;
import java.util.List;

public class PhotoChooser implements Chooser<IPhotoService> {

    private List<IPhotoService> servicePhotoList;

    public PhotoChooser(){
        servicePhotoList = new ArrayList<>();
        DropboxService dropbox = new DropboxService();
        servicePhotoList.add(dropbox);
    }

    @Override
    public List<IPhotoService> getServices() {
        return servicePhotoList;
    }
}
