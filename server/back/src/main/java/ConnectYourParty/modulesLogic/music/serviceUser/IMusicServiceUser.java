package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMusicServiceUser {
    List<MusicService> searchMusic(String music, String serviceName) throws NoSuchServiceException, GetMusicErrorException;

    MusicService getInfoFromId(String id, String serviceName) throws NoSuchServiceException, GetMusicErrorException;
}