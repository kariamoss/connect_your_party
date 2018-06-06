package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.services.photo.IMusicService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MusicServiceUser implements IMusicServiceUser {
    private List<IMusicService> serviceMusicList;

    @PostConstruct
    public void init(){
        serviceMusicList = new ArrayList<>();
    }

    @Override
    public List<MusicService> searchMusic(String music, String serviceName) throws NoSuchServiceException {
        return this.getService(serviceName).searchMusic(music);
    }

    private IMusicService getService(String serviceName) throws NoSuchServiceException {
        for(IMusicService service : serviceMusicList){
            if(service.getServiceName().equals(serviceName)){
                return service;
            }
        }
        throw new NoSuchServiceException("Unknown service " + serviceName);
    }
}
