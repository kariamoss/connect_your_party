package connectYourParty.modulesLogic.music.serviceUser;

import connectYourParty.businessObjects.Token;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.objects.music.MusicService;
import connectYourParty.objects.music.PlaylistService;

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
