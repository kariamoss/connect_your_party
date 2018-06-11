package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.businessObjects.music.Playlist;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.database.token.ITokenDatabase;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.music.AddPlaylistException;
import ConnectYourParty.exception.music.NoSuchPlaylistException;
import ConnectYourParty.exceptions.MissingTokenException;
import ConnectYourParty.exceptions.music.CannotCreatePlaylistException;
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

    @EJB
    ITokenDatabase tokenDB;

    @Override
    public List<MusicSearchHolder> searchMusic(String search, String service) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException {
        List<MusicService> musicService = musicServiceUser.searchMusic(search, service,tokenDB.getTokenFromServiceName(service));
        List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
        for(MusicService ms : musicService){
            musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
        }
        return musicSearchHolders;
    }

    @Override
    public List<MusicSearchHolder> getListMusic(String service) throws NoSuchServiceException, NoSuchPlaylistException, AddPlaylistException, GetMusicErrorException, MissingTokenException {
        List<Playlist> playlists = db.getPlaylistList();
        if (playlists.size() == 0){
            // Empty playlist
            return new ArrayList<>();
        }
        else{
            List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
            List<MusicService> musicService = musicServiceUser.getMusicFromPlaylist(playlists.get(0).getId(),
                    service,tokenDB.getTokenFromServiceName(service));
            for(MusicService ms : musicService){
                musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
            }
            return musicSearchHolders;
        }
    }

    @Override
    public MusicSearchHolder getInfoFromMusicId(String id, String serviceName) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException {
        MusicService musicService = musicServiceUser.getInfoFromMusicId(id, serviceName,tokenDB.getTokenFromServiceName(serviceName));
        return new MusicSearchHolder(musicService.getId(), musicService.getTitle(), musicService.getArtist());
    }

    @Override
    public void addMusic(String idMusic, String serviceName) throws NoSuchServiceException, AddPlaylistException,
            NoSuchPlaylistException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException {
        Playlist playlist;
        List<Playlist> playlists = db.getPlaylistList();
        if (playlists.size() == 0){
            PlaylistService playlistService = musicServiceUser.addPlaylist(serviceName,tokenDB.getTokenFromServiceName(serviceName));
            db.addPlaylist(new Playlist(playlistService.getId(), serviceName));
            playlist = db.getPlaylistFromId(playlistService.getId());
        }
        else{
            playlist = playlists.get(0);
        }
        musicServiceUser.addMusicFromId(idMusic, playlist.getId(), serviceName,tokenDB.getTokenFromServiceName(serviceName));
    }
}
