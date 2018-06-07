package ConnectYourParty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
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

    @Ignore
    public void SearchRequestGoodLength() throws GetMusicErrorException {
        List<MusicService> list = service.searchMusic("booba");
        assertEquals(list.size(), service.searchResults);
    }

    @Ignore
    public void refreshTest() throws GetMusicErrorException {
        service.updateToken();
    }
}

