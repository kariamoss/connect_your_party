package ConnectYourParty.modulesLogic.photo.ServiceUser;


import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.modulesLogic.service.Subscriber;
import ConnectYourParty.requestObjects.photo.PhotoServiceHolder;
import ConnectYourParty.services.IService;
import ConnectYourParty.services.photo.IPhotoService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class PhotoServiceUser implements IPhotoServiceUser, Subscriber {

    @EJB
    private IServiceRegistry serviceRegistry;

    private Collection<ServiceHolder> servicePhotoList;

    private Map<ServiceHolder, Integer> ids;

    private Module module = Module.PHOTO;

    @PostConstruct
    public void init() {
        servicePhotoList = new HashSet<>();
        List<ServiceHolder> serviceHolders = this.serviceRegistry.getServiceHolder();
        ids = new HashMap<>();

        for(ServiceHolder holder : serviceHolders){
            servicePhotoList.add(holder);
            ids.put(holder, holder.getId());
        }

        this.serviceRegistry.subscribe(this, this.module);
    }

    @Override
    public List<PhotoServiceHolder> getServiceList() {

        List<PhotoServiceHolder> arr = new ArrayList<>();
        for (ServiceHolder serv : servicePhotoList) {
            try {
                IService service = serv.getService();
                arr.add(new PhotoServiceHolder(service.getServiceName(),
                        service.getServiceIcon().getHost() + service.getServiceIcon().getPath()));
            } catch (Exception e){

            }
        }

        return arr;
    }

    @Override
    public void removePhoto(Photo photo, Optional<Token> token) throws CannotDeletePhotoException, NoSuchServiceException {
        String serviceHost = photo.getServiceHost();
        // Optional<TokenService> token = photo.getUser().getToken(serviceHost);
        if (token.isPresent()) {
            this.getService(serviceHost).removePhoto(photo.getPrivatePhotoPath(), Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(), token.get().getRefreshToken())));
        }
        this.getService(serviceHost).removePhoto(photo.getPrivatePhotoPath(), Optional.empty());
    }

    @Override
    public byte[] getPhoto(Photo photo, Optional<Token> token) throws RetrievePhotoErrorException, NoSuchServiceException, NoSuchPhotoException {
        String serviceHost = photo.getServiceHost();
        // Optional<TokenService> token = photo.getUser().getToken(serviceHost);
        if (token.isPresent()) {
            return this.getService(serviceHost).getPhoto(photo.getPrivatePhotoPath(), Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(), token.get().getRefreshToken())));
        }
        return this.getService(serviceHost).getPhoto(photo.getPrivatePhotoPath(), Optional.empty());
    }

    @Override
    public void addPhoto(Photo photo, byte[] bin, Optional<Token> token) throws AddPhotoErrorException, NoSuchServiceException {
        String serviceHost = photo.getServiceHost();
        //Optional<TokenService> token = photo.getUser().getToken(serviceHost);
        if (token.isPresent()) {
            this.getService(serviceHost).addPhoto(bin, photo.getPrivatePhotoPath(), Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(), token.get().getRefreshToken())));
            return;
        }
        this.getService(serviceHost).addPhoto(bin, photo.getPrivatePhotoPath(), Optional.empty());
    }

    private IPhotoService getService(String serviceName) throws NoSuchServiceException {

        for (ServiceHolder holder : servicePhotoList) {
            try {
                IPhotoService service = (IPhotoService) holder.getService();
                if (service.getServiceName().equals(serviceName)) {
                    return service;
                }
            } catch (Exception e){

            }
        }

        throw new NoSuchServiceException();
    }

    @Override
    public void onAdd(ServiceHolder holder) {
        try {
            servicePhotoList.add(holder);
            ids.put(holder, holder.getId());
        } catch (Exception e) {

        }
    }

    @Override
    public void onRemove(ServiceHolder holder) {
        try {
            servicePhotoList.remove(holder);
            ids.remove(holder);
        } catch (Exception e) {

        }
    }

    @Override
    public void onUnsubscribe() {
        this.serviceRegistry.subscribe(this, this.module);
    }
}
