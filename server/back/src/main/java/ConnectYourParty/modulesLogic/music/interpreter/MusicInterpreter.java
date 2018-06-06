package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.modulesLogic.music.serviceUser.IMusicServiceUser;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.requestObjects.music.MusicSearchHolder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MusicInterpreter implements IMusicInterpreter {
    @EJB
    DbMock db;

    @EJB
    IMusicServiceUser musicServiceUser;

    @Override
    public List<MusicSearchHolder> searchMusic(String search, String service) throws NoSuchServiceException {
        List<MusicService> musicService = musicServiceUser.searchMusic(search, service);
        List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
        for(MusicService ms : musicService){
            musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
        }
        return musicSearchHolders;
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
