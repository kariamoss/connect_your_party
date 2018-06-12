package connectYourParty.modulesLogic.photo.interpreter;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.businessObjects.photo.Photo;
import connectYourParty.database.photo.IPhotoDatabase;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.exception.photo.NoSuchPhotoException;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.photo.PhotoAlreadyExistException;
import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.exceptions.photo.RetrievePhotoErrorException;
import connectYourParty.modulesLogic.photo.ServiceUser.IPhotoServiceUser;
import connectYourParty.requestObjects.photo.PhotoHolder;
import connectYourParty.requestObjects.photo.PhotoServiceHolder;
import org.apache.myfaces.util.FilenameUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
public class PhotoInterpreter implements IPhotoInterpreter {

    @EJB
    private IPhotoServiceUser services;

    @EJB
    private IPhotoDatabase db;

    @EJB
    private IUserRegistry userRegistry;

    @Override
    public void addPhoto(InputStream stream, String name, String serviceName,String userId) throws IOException, AddPhotoErrorException, PhotoAlreadyExistException, NoSuchUserException {
        String rName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(name);
        Optional<Token> token;
        Photo photo;
        User user = userRegistry.getUserById(userId);


        if (user.getToken(serviceName).isPresent()){
            token = Optional.of(user.getToken(serviceName).get());
            photo = new Photo(rName, serviceName, token.get());
        }
        else{
            token = Optional.empty();
            photo = new Photo(rName, serviceName, null);
        }


        try {

            db.addPhoto(photo);
            byte[] bin = new byte[stream.available()];
            stream.read(bin);
            services.addPhoto(photo, bin, token);
        } catch (Exception e) {
            db.removePhoto(photo);
        }

    }

    @Override
    public byte[] getPhoto(String path) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException {
        Photo photo = db.getPhotoFromPath(path);
        return services.getPhoto(photo, photo.getAccessToken());
    }

    @Override
    public void removePhoto(String path) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException, CannotDeletePhotoException {
        Photo photo = db.getPhotoFromPath(path);
        services.removePhoto(photo, photo.getAccessToken());
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
        return this.services.getServiceList();
    }
}
