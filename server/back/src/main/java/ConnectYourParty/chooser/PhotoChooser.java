package ConnectYourParty.chooser;

import ConnectYourParty.services.photo.IPhotoService;
import ConnectYourParty.services.photo.DriveService;

import java.util.ArrayList;
import java.util.List;

public class PhotoChooser implements Chooser<IPhotoService> {

    List<IPhotoService> servicePhotoList;

    public PhotoChooser(){
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new DriveService());
    }

    @Override
    public IPhotoService getService(){
        return servicePhotoList.get(0);
    }
}
