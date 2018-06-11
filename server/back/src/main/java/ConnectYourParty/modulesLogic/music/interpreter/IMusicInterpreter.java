package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.music.AddPlaylistException;
import ConnectYourParty.exception.music.NoSuchPlaylistException;
import ConnectYourParty.exceptions.MissingTokenException;
import ConnectYourParty.exceptions.music.CannotCreatePlaylistException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.requestObjects.music.MusicSearchHolder;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMusicInterpreter {
    List<MusicSearchHolder> searchMusic(String search, String service) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException;

    List<MusicSearchHolder> getListMusic(String service) throws NoSuchServiceException, NoSuchPlaylistException, AddPlaylistException, GetMusicErrorException, MissingTokenException;

    MusicSearchHolder getInfoFromMusicId(String id, String serviceName) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException;

    void addMusic(String idMusic, String serviceName) throws NoSuchServiceException, AddPlaylistException, NoSuchPlaylistException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException;

    //List<MusicSearchHolder> getListMusic(String event);

    //void addMusicToEvent(String music, String event, String service) throws NoSuchServiceException, AddMusicException, GetMusicErrorException;
}
