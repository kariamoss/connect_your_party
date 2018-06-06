package ConnectYourParty.modulesLogic.photo.ServiceUser;


import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.services.photo.IPhotoService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        String serviceHost = photo.getServiceHost();
        Optional<TokenService> token = photo.getUser().getToken(serviceHost);
        this.getService(serviceHost).removePhoto(photo.getPrivatePhotoPath(), token);
    }

    @Override
    public byte[] getPhoto(Photo photo) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException {
        String serviceHost = photo.getServiceHost();
        Optional<TokenService> token = photo.getUser().getToken(serviceHost);
        return this.getService(serviceHost).getPhoto(photo.getPrivatePhotoPath(), token);
    }

    @Override
    public void addPhoto(Photo photo, byte[] bin) throws AddPhotoErrorException, NoSuchServiceException {
        String serviceHost = photo.getServiceHost();
        Optional<TokenService> token = photo.getUser().getToken(serviceHost);
        this.getService(serviceHost).addPhoto(bin,photo.getPrivatePhotoPath(), token);
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
