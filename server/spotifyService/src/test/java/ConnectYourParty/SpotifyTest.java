package ConnectYourParty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
    private final String token = "AQAZsfm9j8Hc2EJX_gHAhjAA6sHiXr0e6_xn4HICjEylaNEN_Q_zNbNhMlhiTsQhwMh6uO0snLbRMH1mt6KiJlqL7q76BO_7bFhab3sYTjZtDIEhW_mx1t-53RSxMCuTF6g";

    @BeforeClass
    public static void init() {
        service = new SpotifyService();
    }

    /*

    @Before
    public void lucaspd() throws GetMusicErrorException {
        service.updateTestToken();
    }

    @Test
    public void BoobaRequest() throws GetMusicErrorException {
        MusicService m = service.getInfoFromId("6xqAP7kpdgCy8lERQHh29c",Optional.of(new TokenService("code", token, null)));
        assertEquals("Booba",m.getArtist());
        assertEquals("113",m.getTitle());
    }

    @Test
    public void SearchRequestGoodLength() throws GetMusicErrorException {
        List<MusicService> list = service.searchMusic("booba",Optional.of(new TokenService("code", token, null)));
        assertEquals(list.size(), service.searchResults);
    }


    @Test
    public void playlistTest(){
        PlaylistService play = service.addPlaylist(Optional.of(new TokenService("code", token, null)));
        assertNotNull(play.getId());
    }

    @Test
    public void addMusicToPlaylist() throws  Exception{
        Optional<TokenService> opt = Optional.of(new TokenService("code", token, null));
        PlaylistService play = service.addPlaylist(opt);
        String id = "6xqAP7kpdgCy8lERQHh29c";
        service.addMusicFromId("6xqAP7kpdgCy8lERQHh29c" ,play.getId(),opt);
        service.addMusicFromId("6xqAP7kpdgCy8lERQHh29c" ,play.getId(),opt);

        List<MusicService> list = service.getMusicFromPlaylist(play.getId(),opt);

        assertEquals(2,list.size());

        assertEquals(id,list.get(0).getId());
    }
    */

}
