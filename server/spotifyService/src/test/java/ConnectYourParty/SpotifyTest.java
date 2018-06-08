package ConnectYourParty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class SpotifyTest {
    /**
     * Requequette Test :-)
     */
    private static SpotifyService service;

    @BeforeClass
    public static void init() {
        service = new SpotifyService();
    }

    @Test
    public void BoobaRequest() throws GetMusicErrorException {
        MusicService m = service.getInfoFromId("6xqAP7kpdgCy8lERQHh29c");
        assertEquals("Booba",m.getArtist());
        assertEquals("113",m.getTitle());
    }

    @Test
    public void SearchRequestGoodLength() throws GetMusicErrorException {
        List<MusicService> list = service.searchMusic("booba");
        assertEquals(list.size(), service.searchResults);
    }

    @Test
    public void refreshTest() throws GetMusicErrorException {
        service.updateToken();
    }

    @Test
    public void playlistTest(){
        PlaylistService play = service.addPlaylist();
        assertNotNull(play.getId());
    }
}

