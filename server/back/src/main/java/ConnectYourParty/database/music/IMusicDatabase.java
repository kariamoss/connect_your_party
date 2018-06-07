package ConnectYourParty.database.music;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.businessObjects.music.Playlist;
import ConnectYourParty.exception.music.AddMusicException;
import ConnectYourParty.exception.music.NoSuchMusicException;
import ConnectYourParty.exception.music.AddPlaylistException;
import ConnectYourParty.exception.music.NoSuchPlaylistException;

import java.util.List;

public interface IMusicDatabase {
    List<Music> getMusicList();

    List<Playlist> getPlaylistList();

    void addMusic(Music music) throws AddMusicException;

    void addPlaylist(Playlist playlist) throws AddPlaylistException;

    void removeMusic(Music music);

    void removePlaylist(Playlist playlist);

    Music getMusicFromId(String id) throws NoSuchMusicException;

    Playlist getPlaylistFromId(String id) throws NoSuchPlaylistException;

    void clean();
}
