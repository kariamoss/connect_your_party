package ConnectYourParty.modulesLogic.photo.chooser;


import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;

public interface IPhotoChooser{
    void addPhoto(byte[] bin,Photo photo) throws AddPhotoErrorException,NoSuchServiceException;

}
