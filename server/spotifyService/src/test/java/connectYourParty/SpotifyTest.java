package connectYourParty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.exceptions.music.SearchMusicErrorException;
import connectYourParty.objects.TokenService;
import connectYourParty.objects.music.MusicService;
import connectYourParty.objects.music.PlaylistService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * Unit test for simple App.
 */
public class SpotifyTest {
    /**
     * Requequette Test :-)
     */
    private static SpotifyService service;
    private final String refreshToken = "AQAZsfm9j8Hc2EJX_gHAhjAA6sHiXr0e6_xn4HICjEylaNEN_Q_zNbNhMlhiTsQhwMh6uO0snLbRMH1mt6KiJlqL7q76BO_7bFhab3sYTjZtDIEhW_mx1t-53RSxMCuTF6g";

    @BeforeClass
    public static void init() {
        service = new SpotifyService();
    }


    @Test
    public void BoobaRequest() throws GetMusicErrorException, MissingTokenException {
        MusicService m = service.getInfoFromId("6xqAP7kpdgCy8lERQHh29c",Optional.of(
                new TokenService("code", null, refreshToken)));
        assertEquals("Booba",m.getArtist());
        assertEquals("113",m.getTitle());
    }

    @Test
    public void SearchRequestGoodLength() throws GetMusicErrorException, MissingTokenException, SearchMusicErrorException {
        List<MusicService> list = service.searchMusic("booba",Optional.of(
                new TokenService("code", null, refreshToken)));
        assertEquals(list.size(), service.searchResults);
    }


    @Test
    public void playlistTest() throws CannotCreatePlaylistException, MissingTokenException {
        PlaylistService play = service.addPlaylist(Optional.of(new TokenService("code", null, refreshToken)));
        assertNotNull(play.getId());
    }

    @Test
    public void addMusicToPlaylist() throws  Exception{
        Optional<TokenService> opt = Optional.of(new TokenService("code", null, refreshToken));
        PlaylistService play = service.addPlaylist(opt);
        String id = "6xqAP7kpdgCy8lERQHh29c";
        service.addMusicFromId("6xqAP7kpdgCy8lERQHh29c" ,play.getId(),opt);
        service.addMusicFromId("6xqAP7kpdgCy8lERQHh29c" ,play.getId(),opt);

        List<MusicService> list = service.getMusicFromPlaylist(play.getId(),opt);

        assertEquals(2,list.size());

        assertEquals(id,list.get(0).getId());
    }


}
