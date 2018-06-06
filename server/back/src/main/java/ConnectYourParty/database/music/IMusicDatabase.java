package ConnectYourParty.database.music;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.exception.AddMusicException;
import ConnectYourParty.exception.NoSuchMusicException;
import ConnectYourParty.exception.NoSuchPhotoException;

import java.util.List;

public interface IMusicDatabase {
    List<Music> getMusicList();

    void addMusic(Music photo) throws AddMusicException;

    void removeMusic(Music music);

    Music getMusicFromPath(String path) throws NoSuchMusicException;

    void clean();
}
