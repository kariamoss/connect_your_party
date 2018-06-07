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

    private List<IPhotoService> servicePhotoList;
    private Set<IPhotoService> additionalService;

    private Map<IPhotoService, Integer> ids;

    private Module module = Module.PHOTO;

    @PostConstruct
    public void init() {
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new DropboxService());
        servicePhotoList.add(new CotyPhotoService());
        additionalService = new HashSet<>();
        ids = new HashMap<>();

        ids.put(servicePhotoList.get(0), 800);

        this.serviceRegistry.subscribe(this, this.module);
    }

    @Override
    public List<PhotoServiceHolder> getServiceList() {
        List<IPhotoService> res = new ArrayList<>();

        res.addAll(additionalService);
        res.addAll(servicePhotoList);

        List<PhotoServiceHolder> arr = new ArrayList<>();
        for (IPhotoService service : res) {
            arr.add(new PhotoServiceHolder(service.getServiceName(),
                    service.getServiceIcon().getHost() + service.getServiceIcon().getPath()));
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
            this.getService(serviceHost).getPhoto(photo.getPrivatePhotoPath(), Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(), token.get().getRefreshToken())));
        }
        return this.getService(serviceHost).getPhoto(photo.getPrivatePhotoPath(), Optional.empty());
    }

    @Override
    public void addPhoto(Photo photo, byte[] bin, Optional<Token> token) throws AddPhotoErrorException, NoSuchServiceException {
        String serviceHost = photo.getServiceHost();
        // Optional<TokenService> token = photo.getUser().getToken(serviceHost);
        if (token.isPresent()) {
            this.getService(serviceHost).addPhoto(bin, photo.getPrivatePhotoPath(), Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(), token.get().getRefreshToken())));
        }
        this.getService(serviceHost).addPhoto(bin, photo.getPrivatePhotoPath(), Optional.empty());
    }

    private IPhotoService getService(String serviceName) throws NoSuchServiceException {

        for (IPhotoService service : servicePhotoList) {
            if (service.getServiceName().equals(serviceName)) {
                return service;
            }
        }

        for (IPhotoService service : additionalService) {
            if (service.getServiceName().equals(serviceName)) {
                return service;
            }
        }
        throw new NoSuchServiceException();
    }

    @Override
    public void onAdd(ServiceHolder holder) {
        try {
            IPhotoService serv = (IPhotoService) holder.getService();
            additionalService.add(serv);
            ids.put(serv, holder.getId());
        } catch (Exception e) {

        }
    }

    @Override
    public void onRemove(ServiceHolder holder) {
        try {
            IPhotoService serv = (IPhotoService) holder.getService();
            additionalService.remove(serv);
            ids.remove(serv);
        } catch (Exception e) {

        }
    }

    @Override
    public void onUnsubscribe() {
        this.serviceRegistry.subscribe(this, this.module);
    }
}
