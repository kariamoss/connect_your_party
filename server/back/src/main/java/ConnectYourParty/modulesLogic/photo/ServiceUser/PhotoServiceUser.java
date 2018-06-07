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
public class PhotoServiceUser implements IPhotoServiceUser {

    private List<IPhotoService> servicePhotoList;

    @PostConstruct
    public void init() {
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new DropboxService());
        servicePhotoList.add(new CotyPhotoService());
    }

    @Override
    public List<IPhotoService> getServiceList() {
        return servicePhotoList;
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
        throw new NoSuchServiceException();
    }
}
