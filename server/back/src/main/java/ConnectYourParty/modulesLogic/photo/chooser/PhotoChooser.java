package ConnectYourParty.modulesLogic.photo.chooser;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.modulesLogic.photo.ServiceUser.IPhotoServiceUser;
import ConnectYourParty.webInterface.photo.PhotoModule;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class PhotoChooser implements IPhotoChooser {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());

    @EJB
    IPhotoServiceUser services;

    @Override
    public void addPhoto(byte[] bin, Photo photo, Optional<Token> token) throws AddPhotoErrorException,NoSuchServiceException {
        services.addPhoto(photo, bin, token);
    }
}
