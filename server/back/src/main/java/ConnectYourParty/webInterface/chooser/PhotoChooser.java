package ConnectYourParty.webInterface.chooser;

import ConnectYourParty.webInterface.services.photo.IPhotoService;
import ConnectYourParty.webInterface.services.photo.DriveService;

import java.util.List;

public class PhotoChooser implements Chooser<IPhotoService> {

    List<IPhotoService> servicePhotoList;

    public PhotoChooser(){
        servicePhotoList.add(new DriveService());
    }

    @Override
    public IPhotoService getService(){
        return servicePhotoList.get(1);
    }
}
