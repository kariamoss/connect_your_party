package connectYourParty.services.music;


import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.CannotGetUserId;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.exceptions.music.SearchMusicErrorException;
import connectYourParty.objects.TokenService;
import connectYourParty.objects.music.MusicService;
import connectYourParty.objects.music.PlaylistService;
import connectYourParty.services.IService;

import java.util.List;
import java.util.Optional;

/**
 * Implements this interface to develop a service for the music module
 */
public interface IMusicService extends IService {

    /**
     * Research a music
     * @param search The research string asked
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @return List of {@link MusicService} containing the musics
     * @throws GetMusicErrorException
     * @throws MissingTokenException
     * @throws SearchMusicErrorException
     */
    List<MusicService> searchMusic(String search,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException, SearchMusicErrorException;

    /**
     * Retrieve info asked in a {@link MusicService} depending on the music id
     * @param id music id
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @return {@link MusicService} containing the music information
     * @throws GetMusicErrorException
     * @throws MissingTokenException
     */
    MusicService getInfoFromId(String id,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException;

    /**
     * Add the given music in the given playlist
     * @param id The music id
     * @param playlist The music playlist
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @throws GetMusicErrorException
     * @throws MissingTokenException
     * @throws CannotGetUserId
     */
    void addMusicFromId(String id, String playlist,Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException, CannotGetUserId;

    /**
     * Add a playlist and give back its id
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @return {@link PlaylistService} containing the playlist information
     * @throws MissingTokenException
     * @throws GetMusicErrorException
     * @throws CannotCreatePlaylistException
     */
    PlaylistService addPlaylist(Optional<TokenService> token) throws MissingTokenException, GetMusicErrorException, CannotCreatePlaylistException;

    /**
     * Retrieve the musics from a given playlist
     * @param playlist The playlist id
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @return The musics in the given playlist
     * @throws MissingTokenException
     * @throws GetMusicErrorException
     */
    List<MusicService> getMusicFromPlaylist(String playlist,Optional<TokenService> token) throws MissingTokenException, GetMusicErrorException;
}
