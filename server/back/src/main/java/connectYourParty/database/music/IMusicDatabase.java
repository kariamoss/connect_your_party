package connectYourParty.database.music;

import connectYourParty.businessObjects.music.Music;
import connectYourParty.businessObjects.music.Playlist;
import connectYourParty.exception.music.AddMusicException;
import connectYourParty.exception.music.NoSuchMusicException;
import connectYourParty.exception.music.AddPlaylistException;
import connectYourParty.exception.music.NoSuchPlaylistException;

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
