package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.exception.NoSuchServiceException;

import javax.ejb.Local;
import javax.json.JsonArray;
import java.util.List;

@Local
public interface IMusicInterpreter {
    JsonArray searchMusic(String search, String service) throws NoSuchServiceException;

    List<Music> getListMusic(String event);

    void addMusicToEvent(String music, String event);
}
