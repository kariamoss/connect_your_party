package ConnectYourParty.modulesLogic.photo.chooser;


import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;

import javax.ejb.Local;

@Local
public interface IPhotoChooser{
    void addPhoto(byte[] bin,Photo photo) throws AddPhotoErrorException,NoSuchServiceException;

}
