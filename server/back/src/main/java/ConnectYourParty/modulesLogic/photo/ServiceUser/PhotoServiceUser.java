package ConnectYourParty.modulesLogic.photo.ServiceUser;


import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.modulesLogic.service.Subscriber;
import ConnectYourParty.requestObjects.photo.PhotoServiceHolder;
import ConnectYourParty.services.photo.IPhotoService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class PhotoServiceUser implements IPhotoServiceUser,Subscriber{

    @EJB private IServiceRegistry serviceRegistry;

    private List<IPhotoService> servicePhotoList;
    private Set<IPhotoService> additionalService;

    private Map<IPhotoService,Integer> ids;

    private Module module = Module.PHOTO;

    @PostConstruct
    public void init(){
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new CotyPhotoService());
        additionalService = new HashSet<>();
        ids = new HashMap<>();

        ids.put(servicePhotoList.get(0),800);

        this.serviceRegistry.subscribe(this,this.module);
    }

    @Override
    public List<PhotoServiceHolder> getServiceList() {
        List<IPhotoService> res = new ArrayList<>();

        res.addAll(additionalService);
        res.addAll(servicePhotoList);

        List<PhotoServiceHolder> arr = new ArrayList<>();
        for (IPhotoService service : res) {
            if (service.getOAuthUrl() == null) {
                arr.add(new PhotoServiceHolder(service.getServiceName(),
                        service.getServiceIcon().getHost() + service.getServiceIcon().getPath()));
                continue;
            }
            arr.add(new PhotoServiceHolder(service.getServiceName(),
                    service.getServiceIcon().getHost() + service.getServiceIcon().getPath(),
                    service.getOAuthUrl().getHost() + service.getOAuthUrl().getPath(),
                    service.getOAuthToken().getHost() + service.getOAuthToken().getPath(),
                    service.getAppKey(),
                    service.getAppSecret(),
                            ids.get(service))
                    );
        }

        return arr;
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

        for(IPhotoService service : additionalService){
            if(service.getServiceName().equals(serviceName)){
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
            ids.put(serv,holder.getId());
        } catch (Exception e){

        }
    }

    @Override
    public void onRemove(ServiceHolder holder) {
        try {
            IPhotoService serv = (IPhotoService) holder.getService();
            additionalService.remove( serv);
            ids.remove(serv);
        } catch (Exception e){

        }
    }

    @Override
    public void onUnsubscribe() {
        this.serviceRegistry.subscribe(this,this.module);
    }
}
