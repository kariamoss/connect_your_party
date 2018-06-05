package ConnectYourParty.photo;

import ConnectYourParty.businessObjects.User;
import ConnectYourParty.businessObjects.photo.Photo;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PhotoTest {
    private User user = new User("salut","salut");

    @Test
    public void photoEqualTest(){
        Photo photo1 = new Photo("salut","salut", this.user,"dropbox");
        assertTrue(photo1.equals(photo1));
    }

    @Test
    public void photoNotEqualTest(){
        Photo photo1 = new Photo("salut","salut", this.user,"dropbox");
        Photo photo2 = new Photo("azeaze","salut", this.user,"dropbox");

        assertFalse(photo1.equals(photo2));
    }
}
