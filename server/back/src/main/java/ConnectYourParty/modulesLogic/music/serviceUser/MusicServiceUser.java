package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.services.photo.IMusicService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.json.JsonArray;
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
    public JsonArray searchMusic(String music, String serviceName) throws NoSuchServiceException {
        MusicService musicService = this.getService(serviceName).searchMusic(music);
        Music musicGot = new Music();
        return null;
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
