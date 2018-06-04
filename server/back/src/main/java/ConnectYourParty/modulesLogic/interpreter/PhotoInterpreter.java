package ConnectYourParty.modulesLogic.interpreter;

import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.ServiceUser.IPhotoServiceUser;
import ConnectYourParty.modulesLogic.chooser.IPhotoChooser;
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
    DbMock db;

    @Override
    public void addPhoto(InputStream stream, String name,String serviceName) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException {
        String path = db.getEvent().getId() + "/"
                + UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(name);

        Photo photo = new Photo(path, name, db.getUser(),serviceName);
        byte[] bin = new byte[stream.available()];
        stream.read(bin);
        try {
            path = "ConnectYourParty/" + path;
            db.addPhoto(db.getEvent(), photo);
            photoChooser.addPhoto(bin, photo);
        } catch (Exception e){
            db.removePhotoFromEvent(db.getEvent(),photo);
        }

    }

    @Override
    public byte[] getPhoto(String path) throws RetrievePhotoErrorException,NoSuchServiceException, NoSuchPhotoException {
        Photo photo = db.getPhotoFromPath(path);
        return services.getPhoto(photo);
    }

    @Override
    public void removePhoto(String path) throws RetrievePhotoErrorException,NoSuchServiceException, NoSuchPhotoException,CannotDeletePhotoException {
        Photo photo = db.getPhotoFromPath(path);
        services.removePhoto(photo);
    }

    @Override
    public List<PhotoHolder> getPhotoList() {
        List<PhotoHolder> arr = new ArrayList<>();

        for (Photo photo : db.getPhotosFromEvent()) {
            arr.add(new PhotoHolder(photo));
        }
        return arr;
    }

    @Override
    public List<PhotoServiceHolder> getServices() {
        List<PhotoServiceHolder> arr = new ArrayList<>();
        for(IPhotoService service : services.getServiceList()){
            arr.add(new PhotoServiceHolder(service.getServiceName(),
                    service.getServiceIcon().getHost()+service.getServiceIcon().getPath()));
        }

        return arr;
    }
}
