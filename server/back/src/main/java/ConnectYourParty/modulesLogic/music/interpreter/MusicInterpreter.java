package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.modulesLogic.music.serviceUser.IMusicServiceUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonArray;
import java.util.List;

@Stateless
public class MusicInterpreter implements IMusicInterpreter {
    @EJB
    DbMock db;

    @EJB
    IMusicServiceUser musicServiceUser;

    @Override
    public JsonArray searchMusic(String search, String service) throws NoSuchServiceException {
        return musicServiceUser.searchMusic(search, service);
    }

    @Override
    public List<Music> getListMusic(String event) {
        //return db.getMusicFromEvent(event);
        return null;
    }

    @Override
    public void addMusicToEvent(String music, String event) {
        //db.addMusicToEvent(music, event);
    }
}
