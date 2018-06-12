package connectYourParty.services.music;


import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.objects.TokenService;
import connectYourParty.objects.music.MusicService;
import connectYourParty.objects.music.PlaylistService;
import connectYourParty.services.IService;

import java.util.List;
import java.util.Optional;

public interface IMusicService extends IService {

    List<MusicService> searchMusic(String search,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException;

    MusicService getInfoFromId(String id,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException;

    void addMusicFromId(String id, String playlist,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException;

    PlaylistService addPlaylist(Optional<TokenService> token) throws MissingTokenException, GetMusicErrorException, CannotCreatePlaylistException;

    List<MusicService> getMusicFromPlaylist(String playlist,Optional<TokenService> token) throws MissingTokenException, GetMusicErrorException;
}
