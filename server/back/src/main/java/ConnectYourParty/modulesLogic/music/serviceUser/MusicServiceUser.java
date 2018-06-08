package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.SpotifyService;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
import ConnectYourParty.services.music.IMusicService;

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
        serviceMusicList.add(new SpotifyService());
    }

    @Override
    public List<MusicService> searchMusic(String music, String serviceName) throws NoSuchServiceException, GetMusicErrorException {
        return this.getService(serviceName).searchMusic(music);
    }

    @Override
    public MusicService getInfoFromMusicId(String id, String serviceName) throws NoSuchServiceException, GetMusicErrorException {
        return this.getService(serviceName).getInfoFromId(id);
    }

    @Override
    public void addMusicFromId(String id, String playlist, String serviceName) throws NoSuchServiceException, GetMusicErrorException {
        this.getService(serviceName).addMusicFromId(id, playlist);
    }

    @Override
    public List<MusicService> getMusicFromPlaylist(String playlist, String serviceName) throws NoSuchServiceException {
        return this.getService(serviceName).getMusicFromPlaylist(playlist);
    }

    @Override
    public PlaylistService addPlaylist(String serviceName) throws NoSuchServiceException {
        return this.getService(serviceName).addPlaylist();
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
