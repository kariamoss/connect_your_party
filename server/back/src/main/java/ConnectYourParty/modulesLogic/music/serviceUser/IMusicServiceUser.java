package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.objects.music.MusicService;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMusicServiceUser {
    List<MusicService> searchMusic(String music, String serviceName) throws NoSuchServiceException;

    MusicService getInfoFromId(String id, String serviceName) throws NoSuchServiceException;
}
