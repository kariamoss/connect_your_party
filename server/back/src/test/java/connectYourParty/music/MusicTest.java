package connectYourParty.music;

import connectYourParty.businessObjects.music.Music;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class MusicTest {

    @Test
    public void musicEqualTest(){
        Music music = new Music("salut", "Spotify", "Pitbull", "Dalle");
        assertEquals(music, music);
    }

    @Test
    public void musicNotEqualTest(){
        Music music1 = new Music("salut", "Spotify", "Pitbull", "Dalle");
        Music music2 = new Music("hey", "Spotify", "Pitbull", "Dalle");

        assertNotEquals(music1, music2);
    }
}
