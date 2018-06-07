package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.requestObjects.music.MusicSearchHolder;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMusicInterpreter {
    List<MusicSearchHolder> searchMusic(String search, String service) throws NoSuchServiceException, GetMusicErrorException;

    List<MusicSearchHolder> getListMusic(String playlist, String service) throws NoSuchServiceException;

    //List<MusicSearchHolder> getListMusic(String event);

    //void addMusicToEvent(String music, String event, String service) throws NoSuchServiceException, AddMusicException, GetMusicErrorException;


}
