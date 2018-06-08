package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.businessObjects.music.Playlist;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.music.AddPlaylistException;
import ConnectYourParty.exception.music.NoSuchPlaylistException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.modulesLogic.music.serviceUser.IMusicServiceUser;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
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
    public List<MusicSearchHolder> getListMusic(String service) throws NoSuchServiceException, NoSuchPlaylistException {
        List<Playlist> playlists = db.getPlaylistList();
        if (playlists.size() == 0){
            throw new NoSuchPlaylistException();
        }
        else{
            List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
            List<MusicService> musicService = musicServiceUser.getMusicFromPlaylist(playlists.get(0).getId(), service);
            for(MusicService ms : musicService){
                musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
            }
            return musicSearchHolders;
        }
    }

    @Override
    public MusicSearchHolder getInfoFromMusicId(String id, String serviceName) throws NoSuchServiceException, GetMusicErrorException {
        MusicService musicService = musicServiceUser.getInfoFromMusicId(id, serviceName);
        return new MusicSearchHolder(musicService.getId(), musicService.getTitle(), musicService.getArtist());
    }

    @Override
    public void addMusic(String idMusic, String serviceName) throws NoSuchServiceException, AddPlaylistException, NoSuchPlaylistException, GetMusicErrorException {
        Playlist playlist;
        List<Playlist> playlists = db.getPlaylistList();
        if (playlists.size() == 0){
            PlaylistService playlistService = musicServiceUser.addPlaylist(serviceName);
            db.addPlaylist(new Playlist(playlistService.getId(), serviceName));
            playlist = db.getPlaylistFromId(playlistService.getId());
        }
        else{
            playlist = playlists.get(0);
        }
        musicServiceUser.addMusicFromId(idMusic, playlist.getId(), serviceName);
    }
}
