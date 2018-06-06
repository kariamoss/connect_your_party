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
import ConnectYourParty.services.photo.IPhotoService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class PhotoServiceUser implements IPhotoServiceUser{

    @EJB private IServiceRegistry serviceRegistry;

    private List<IPhotoService> servicePhotoList;
    private Set<IPhotoService> additionalService;

    private Module module = Module.PHOTO;

    @PostConstruct
    public void init(){
        servicePhotoList = new ArrayList<>();
        servicePhotoList.add(new CotyPhotoService());

        additionalService = new HashSet<>();
        updateAddService();
    }

    public void updateAddService(){
        List<ServiceHolder> res = serviceRegistry.getServiceHolderFromModule(module);

        for(ServiceHolder holder : res){
            try {
                additionalService.add((IPhotoService) holder.getService());
            } catch (Exception e){

            }
        }
    }

    @Override
    public List<IPhotoService> getServiceList() {
        updateAddService();
        List<IPhotoService> res = new ArrayList<>();

        res.addAll(additionalService);
        res.addAll(servicePhotoList);

        return res;
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
        updateAddService();

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
}
