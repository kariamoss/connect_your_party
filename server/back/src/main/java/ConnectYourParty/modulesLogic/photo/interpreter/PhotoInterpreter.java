package ConnectYourParty.modulesLogic.photo.interpreter;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.user.IUserRegistry;
import ConnectYourParty.exception.NoSuchUserException;
import ConnectYourParty.exception.photo.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.photo.PhotoAlreadyExistException;
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

        if (user.getToken(serviceName).isPresent())
            token = Optional.of(user.getToken(serviceName).get());
        else token = Optional.empty();

        photo = new Photo(rName, serviceName, token.isPresent()?token.get():null);

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
