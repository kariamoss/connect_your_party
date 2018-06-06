package ConnectYourParty.music;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.businessObjects.photo.Photo;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class MusicTest {

    @Test
    public void musicEqualTest(){
        Music music = new Music("salut", "spotify", "Pitbull", "Dalle");
        assertEquals(music, music);
    }

    @Test
    public void photoNotEqualTest(){
        Music music1 = new Music("salut", "spotify", "Pitbull", "Dalle");
        Music music2 = new Music("hey", "spotify", "Pitbull", "Dalle");

        assertNotEquals(music1, music2);
    }
}
