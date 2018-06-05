package ConnectYourParty.modulesLogic.photo.ServiceUser;


import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.services.photo.IPhotoService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PhotoServiceUser implements IPhotoServiceUser{

    private List<IPhotoService> servicePhotoList;

    @PostConstruct
    public void init(){
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new DropboxService());
        servicePhotoList.add(new CotyPhotoService());
    }

    @Override
    public List<IPhotoService> getServiceList() {
        return servicePhotoList;
    }

    @Override
    public void removePhoto(Photo photo) throws CannotDeletePhotoException, NoSuchServiceException {
        this.getService(photo.getServiceHost()).removePhoto(photo.getPrivatePhotoPath());
    }

    @Override
    public byte[] getPhoto(Photo photo) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException {
        return this.getService(photo.getServiceHost()).getPhoto(photo.getPrivatePhotoPath());
    }

    @Override
    public void addPhoto(Photo photo, byte[] bin) throws AddPhotoErrorException, NoSuchServiceException {
        this.getService(photo.getServiceHost()).addPhoto(bin,photo.getPrivatePhotoPath());
    }

    private IPhotoService getService(String serviceName) throws NoSuchServiceException{
        for(IPhotoService service : servicePhotoList){
            if(service.getServiceName().equals(serviceName)){
                return service;
            }
        }
        throw new NoSuchServiceException();
    }
}
