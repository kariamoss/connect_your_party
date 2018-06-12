package ConnectYourParty.modulesLogic.music.interpreter;

import ConnectYourParty.businessObjects.music.Playlist;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.database.user.IUserRegistry;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.NoSuchUserException;
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
    IUserRegistry userRegistry;

    @Override
    public List<MusicSearchHolder> searchMusic(String search, String service) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException, NoSuchUserException {
        List<MusicService> musicService = musicServiceUser.searchMusic(search, service,userRegistry.getUserById(DbMock.user.getName()).getToken(service));
        List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
        for(MusicService ms : musicService){
            musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
        }
        return musicSearchHolders;
    }

    @Override
    public List<MusicSearchHolder> getListMusic(String service) throws NoSuchServiceException, NoSuchPlaylistException, AddPlaylistException, GetMusicErrorException, MissingTokenException, NoSuchUserException {
        List<Playlist> playlists = db.getPlaylistList();
        if (playlists.size() == 0){
            // Empty playlist
            return new ArrayList<>();
        }
        else{
            List<MusicSearchHolder> musicSearchHolders = new ArrayList<>();
            List<MusicService> musicService = musicServiceUser.getMusicFromPlaylist(playlists.get(0).getId(),
                    service,userRegistry.getUserById(DbMock.user.getName()).getToken(service));
            for(MusicService ms : musicService){
                musicSearchHolders.add(new MusicSearchHolder(ms.getId(), ms.getTitle(), ms.getArtist()));
            }
            return musicSearchHolders;
        }
    }

    @Override
    public MusicSearchHolder getInfoFromMusicId(String id, String serviceName) throws NoSuchServiceException,
            GetMusicErrorException, MissingTokenException, NoSuchUserException {
        MusicService musicService = musicServiceUser.getInfoFromMusicId(id,
                serviceName,userRegistry.getUserById(DbMock.user.getName()).getToken(serviceName));
        return new MusicSearchHolder(musicService.getId(), musicService.getTitle(), musicService.getArtist());
    }

    @Override
    public void addMusic(String idMusic, String serviceName) throws NoSuchServiceException, AddPlaylistException,
            NoSuchPlaylistException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException, NoSuchUserException {
        Playlist playlist;
        List<Playlist> playlists = db.getPlaylistList();
        if (playlists.size() == 0){
            PlaylistService playlistService = musicServiceUser.addPlaylist(serviceName,
                    userRegistry.getUserById(DbMock.user.getName()).getToken(serviceName));
            db.addPlaylist(new Playlist(playlistService.getId(), serviceName));
            playlist = db.getPlaylistFromId(playlistService.getId());
        }
        else{
            playlist = playlists.get(0);
        }
        musicServiceUser.addMusicFromId(idMusic, playlist.getId(), serviceName,
                userRegistry.getUserById(DbMock.user.getName()).getToken(serviceName));
    }
}
