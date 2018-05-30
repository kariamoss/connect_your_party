package ConnectYourParty.chooser;

import ConnectYourParty.DropboxService;
import ConnectYourParty.requestObjects.photo.PhotoServiceHolder;
import ConnectYourParty.requestObjects.photo.UploadRequest;
import ConnectYourParty.services.photo.IPhotoService;

import java.util.ArrayList;
import java.util.List;

public class PhotoChooser implements Chooser<IPhotoService,PhotoServiceHolder> {

    private List<IPhotoService> servicePhotoList;

    public PhotoChooser(){
        servicePhotoList = new ArrayList<>();
        DropboxService dropbox = new DropboxService();
        servicePhotoList.add(dropbox);
    }

    @Override
    public List<PhotoServiceHolder> getServices() {
        List<PhotoServiceHolder> arr = new ArrayList<>();
        for(IPhotoService service : servicePhotoList){
            arr.add(new PhotoServiceHolder(service.getServiceName(),
                    service.getServiceIcon().getHost()+service.getServiceIcon().getPath()));
        }

        return arr;
    }

    public void addPhoto(UploadRequest req){
        servicePhotoList.get(0).addPhoto(req.photo.getBytes(),req.name);
    }
}
