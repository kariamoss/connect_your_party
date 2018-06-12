package connectYourParty.modulesLogic.photo.ServiceUser;


import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.photo.Photo;
import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.exception.photo.NoSuchPhotoException;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.exceptions.photo.RetrievePhotoErrorException;
import connectYourParty.objects.TokenService;
import connectYourParty.modulesLogic.service.Subscriber;
import connectYourParty.requestObjects.photo.PhotoServiceHolder;
import connectYourParty.services.IService;
import connectYourParty.services.photo.IPhotoService;

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
