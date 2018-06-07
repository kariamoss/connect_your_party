package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.modulesLogic.music.serviceUser.IMusicServiceUser;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.requestObjects.music.MusicSearchHolder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MusicInterpreter implements IMusicInterpreter {
    @EJB
    IMusicDatabase db;

    @EJB
    IMusicServiceUser musicServiceUser;

    @Override
    public List<MusicSearchHolder> searchMusic(String search, String service) throws NoSuchServiceException, GetMusicErrorException {
        List<MusicService> musicService = musicServiceUser.searchMusic(search, service);
        List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
        for(MusicService ms : musicService){
            musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
        }
        return musicSearchHolders;
    }

    @Override
    public List<MusicSearchHolder> getListMusic(String playlist) {
        List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
        List<MusicService> musicService = musicServiceUser.getMusicFromPlaylist(playlist);
        for(MusicService ms : musicService){
            musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
        }
        return musicSearchHolders;
    }

    /*@Override
    public void addMusicToEvent(String music, String event, String service) throws NoSuchServiceException, AddMusicException, GetMusicErrorException {
        MusicService musicService = musicServiceUser.getInfoFromId(music, service);
        db.addMusic(new Music(music, service, musicService.getTitle(), musicService.getArtist()));
    }*/
}
