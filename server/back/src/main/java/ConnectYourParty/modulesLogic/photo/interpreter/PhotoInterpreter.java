package ConnectYourParty.modulesLogic.photo.interpreter;

import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.photo.ServiceUser.IPhotoServiceUser;
import ConnectYourParty.modulesLogic.photo.chooser.IPhotoChooser;
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import ConnectYourParty.requestObjects.photo.PhotoServiceHolder;
import ConnectYourParty.services.photo.IPhotoService;
import org.apache.myfaces.util.FilenameUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stateless
public class PhotoInterpreter implements IPhotoInterpreter {

    @EJB
    IPhotoServiceUser services;

    @EJB
    IPhotoChooser photoChooser;

    @EJB
    IPhotoDatabase db;

    @Override
    public void addPhoto(InputStream stream, String name, String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException {
        String rName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(name);

        Photo photo = new Photo(rName, serviceName);


        try {
            db.addPhoto(photo);
            byte[] bin = new byte[stream.available()];
            stream.read(bin);
            photoChooser.addPhoto(bin, photo);
        } catch (Exception e){
            db.removePhoto(photo);
        }

    }

    @Override
    public byte[] getPhoto(String path) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException {
        Photo photo = db.getPhotoFromPath(path);
        return services.getPhoto(photo);
    }

    @Override
    public void removePhoto(String path) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException, CannotDeletePhotoException {
        Photo photo = db.getPhotoFromPath(path);
        services.removePhoto(photo);
    }

    @Override
    public List<PhotoHolder> getPhotoList() {
        List<PhotoHolder> arr = new ArrayList<>();

        for (Photo photo : db.getPhotoList()) {
            arr.add(new PhotoHolder(photo));
        }
        return arr;
    }

    @Override
    public List<PhotoServiceHolder> getServices() {
        List<PhotoServiceHolder> arr = new ArrayList<>();
        for (IPhotoService service : services.getServiceList()) {
            if (service.getOAuthUrl() == null) {
                arr.add(new PhotoServiceHolder(service.getServiceName(),
                        service.getServiceIcon().getHost() + service.getServiceIcon().getPath()));
                continue;
            }
            arr.add(new PhotoServiceHolder(service.getServiceName(),
                    service.getServiceIcon().getHost() + service.getServiceIcon().getPath(),
                    service.getOAuthUrl().getHost() + service.getOAuthUrl().getPath(),
                    service.getOAuthToken().getHost() + service.getOAuthToken().getPath(),
                    service.getAppKey(),
                    service.getAppSecret()));
        }

        return arr;
    }
}
