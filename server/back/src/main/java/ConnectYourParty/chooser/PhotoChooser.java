package ConnectYourParty.chooser;

import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.requestObjects.photo.UploadRequest;
import ConnectYourParty.services.photo.IPhotoService;
import ConnectYourParty.webInterface.photo.PhotoModule;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoChooser implements Chooser<IPhotoService> {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());

    private List<IPhotoService> servicePhotoList;

    public PhotoChooser(){
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new DropboxService());
        servicePhotoList.add(new CotyPhotoService());
    }

    @Override
    public List<IPhotoService> getServices() {
        return servicePhotoList;
    }

    public void addPhoto(UploadRequest req){
        try {
            servicePhotoList.get(0).addPhoto(req.photo.getBytes(),req.name);
        } catch (AddPhotoErrorException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
