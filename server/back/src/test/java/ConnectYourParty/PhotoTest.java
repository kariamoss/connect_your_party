package ConnectYourParty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ConnectYourParty.requestObjects.Photo;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class PhotoTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void checkTest()
    {
        Photo photo = new Photo();
        assertFalse(photo.check());
    }

    @Test
    public void oneElementTest(){
        Photo photo = new Photo();
        photo.name = "salut";
        assertFalse(photo.check());
    }

    @Test
    public void allElemTest(){
        Photo photo = new Photo();
        photo.name = "";
        photo.format = "";
        photo.serviceName ="";
        photo.photo = "";

        assertTrue(photo.check());
    }
}
