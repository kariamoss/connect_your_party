package ConnectYourParty.services.music;


import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
import ConnectYourParty.services.IService;

import java.util.List;

public interface IMusicService extends IService {

    List<MusicService> searchMusic(String search) throws GetMusicErrorException;

    MusicService getInfoFromId(String id) throws GetMusicErrorException;

    void addMusicFromId(String id, String playlist) throws GetMusicErrorException;

    PlaylistService addPlaylist();

    List<MusicService> getMusicFromPlaylist(String playlist);
}
