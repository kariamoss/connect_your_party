package connectYourParty.modulesLogic.music.interpreter;

import connectYourParty.businessObjects.music.Playlist;
import connectYourParty.database.DbMock;
import connectYourParty.database.music.IMusicDatabase;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.exception.music.AddPlaylistException;
import connectYourParty.exception.music.NoSuchPlaylistException;
import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.modulesLogic.music.serviceUser.IMusicServiceUser;
import connectYourParty.objects.music.MusicService;
import connectYourParty.objects.music.PlaylistService;
import connectYourParty.requestObjects.music.MusicSearchHolder;

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

        Playlist playlist = getEventPlaylist(serviceName);
        musicServiceUser.addMusicFromId(idMusic, playlist.getId(), serviceName,
                userRegistry.getUserById(DbMock.user.getName()).getToken(serviceName));
    }

    @Override
    public String getPlaylistUrlFromEvent(String service, String event) throws NoSuchPlaylistException, NoSuchUserException, NoSuchServiceException, MissingTokenException, GetMusicErrorException, CannotCreatePlaylistException, AddPlaylistException {
        return getEventPlaylist(service).getId();
    }

    private Playlist getEventPlaylist(String service) throws NoSuchUserException, AddPlaylistException, NoSuchPlaylistException, CannotCreatePlaylistException, MissingTokenException, GetMusicErrorException, NoSuchServiceException {
        Playlist playlist;
        List<Playlist> playlists = db.getPlaylistList();

        if (playlists.size() == 0){
            PlaylistService playlistService = musicServiceUser.addPlaylist(service,
                    userRegistry.getUserById(DbMock.user.getName()).getToken(service));
            db.addPlaylist(new Playlist(playlistService.getId(), service));
            playlist = db.getPlaylistFromId(playlistService.getId());
        }
        else{
            playlist = playlists.get(0);
        }
        return playlist;
    }
}
