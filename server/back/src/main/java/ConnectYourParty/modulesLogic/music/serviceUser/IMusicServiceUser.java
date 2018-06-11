package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.MissingTokenException;
import ConnectYourParty.exceptions.music.CannotCreatePlaylistException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface IMusicServiceUser {
    List<MusicService> searchMusic(String music, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException;

    MusicService getInfoFromMusicId(String id, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException;

    void addMusicFromId(String id, String playlist, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException;

    List<MusicService> getMusicFromPlaylist(String playlist, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException;

    PlaylistService addPlaylist(String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException;
}
