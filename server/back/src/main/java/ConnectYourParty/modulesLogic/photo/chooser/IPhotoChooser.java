package ConnectYourParty.modulesLogic.photo.chooser;


import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface IPhotoChooser{
    void addPhoto(byte[] bin, Photo photo, Optional<Token> token) throws AddPhotoErrorException,NoSuchServiceException;

}
