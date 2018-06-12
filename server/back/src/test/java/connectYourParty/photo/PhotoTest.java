package connectYourParty.photo;

import connectYourParty.businessObjects.User;
import connectYourParty.businessObjects.photo.Photo;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PhotoTest {
    private User user = new User("salut","salut");

    @Test
    public void photoEqualTest(){
        Photo photo1 = new Photo("salut", "dropbox");
        assertTrue(photo1.equals(photo1));
    }

    @Test
    public void photoNotEqualTest(){
        Photo photo1 = new Photo("salut", "dropbox");
        Photo photo2 = new Photo("test", "dropbox");

        assertFalse(photo1.equals(photo2));
    }
}
