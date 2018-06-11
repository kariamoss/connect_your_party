package ConnectYourParty.services.music;


import ConnectYourParty.exceptions.MissingTokenException;
import ConnectYourParty.exceptions.music.CannotCreatePlaylistException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
import ConnectYourParty.services.IService;

import java.util.List;
import java.util.Optional;

public interface IMusicService extends IService {

    List<MusicService> searchMusic(String search,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException;

    MusicService getInfoFromId(String id,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException;

    void addMusicFromId(String id, String playlist,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException;

    PlaylistService addPlaylist(Optional<TokenService> token) throws MissingTokenException, GetMusicErrorException, CannotCreatePlaylistException;

    List<MusicService> getMusicFromPlaylist(String playlist,Optional<TokenService> token) throws MissingTokenException, GetMusicErrorException;
}
