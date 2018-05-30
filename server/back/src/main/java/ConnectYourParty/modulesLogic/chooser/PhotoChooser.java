package ConnectYourParty.modulesLogic.chooser;

import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.IPhoto;
import ConnectYourParty.requestObjects.photo.PhotoServiceHolder;
import ConnectYourParty.services.photo.IPhotoService;
import ConnectYourParty.webInterface.photo.PhotoModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PhotoChooser implements Chooser<IPhotoService,PhotoServiceHolder>, IPhoto {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());

    private List<IPhotoService> servicePhotoList;

    public PhotoChooser(){
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new DropboxService());
        servicePhotoList.add(new CotyPhotoService());
    }

    public void addPhoto(InputStream stream, String path, String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException {
        byte[] buff = new byte[stream.available()];
        stream.read(buff);
        servicePhotoList.get(0).addPhoto(buff, path);
    }

    @Override
    public void getPhotos(String path) throws RetrievePhotoErrorException {
        servicePhotoList.get(0).getPhoto(path);
    }

    @Override
    public void removePhoto(String path) throws CannotDeletePhotoException {
        servicePhotoList.get(0).removePhoto(path);
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
}
