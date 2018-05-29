package ConnectYourParty.chooser;

import ConnectYourParty.services.photo.IPhotoService;

import java.util.ArrayList;
import java.util.List;

public class PhotoChooser implements Chooser<IPhotoService> {

    private List<IPhotoService> servicePhotoList;

    public PhotoChooser(){
        servicePhotoList = new ArrayList<>();
        DriveService driveService = new DriveService();
        servicePhotoList.add(driveService);
    }

    @Override
    public List<IPhotoService> getServices() {
        return servicePhotoList;
    }
}
