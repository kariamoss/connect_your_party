package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMusicServiceUser {
    List<MusicService> searchMusic(String music, String serviceName) throws NoSuchServiceException, GetMusicErrorException;

    MusicService getInfoFromMusicId(String id, String serviceName) throws NoSuchServiceException, GetMusicErrorException;

    void addMusicFromId(String id, String playlist, String serviceName) throws NoSuchServiceException, GetMusicErrorException;

    List<MusicService> getMusicFromPlaylist(String playlist, String serviceName) throws NoSuchServiceException;

    PlaylistService addPlaylist(String serviceName) throws NoSuchServiceException;
}
