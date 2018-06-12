package connectYourParty.modulesLogic.music.interpreter;

import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.exception.music.AddPlaylistException;
import connectYourParty.exception.music.NoSuchPlaylistException;
import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.requestObjects.music.MusicSearchHolder;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMusicInterpreter {
    List<MusicSearchHolder> searchMusic(String search, String service) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException, NoSuchUserException;

    List<MusicSearchHolder> getListMusic(String service) throws NoSuchServiceException, NoSuchPlaylistException, AddPlaylistException, GetMusicErrorException, MissingTokenException, NoSuchUserException;

    MusicSearchHolder getInfoFromMusicId(String id, String serviceName) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException, NoSuchUserException;

    void addMusic(String idMusic, String serviceName) throws NoSuchServiceException, AddPlaylistException, NoSuchPlaylistException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException, NoSuchUserException;

    String getPlaylistUrlFromEvent(String service, String event) throws NoSuchPlaylistException, NoSuchUserException, NoSuchServiceException, MissingTokenException, GetMusicErrorException, CannotCreatePlaylistException, AddPlaylistException;

    //List<MusicSearchHolder> getListMusic(String event);

    //void addMusicToEvent(String music, String event, String service) throws NoSuchServiceException, AddMusicException, GetMusicErrorException;
}
