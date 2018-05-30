package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class DbTest{
    @Before
    public void init(){
        DbMock.clean();
    }

    @Test(expected = PhotoAlreadyExistException.class)
    public void doublePhoto() throws PhotoAlreadyExistException{
        Photo photo1 = new Photo("salut","salut", DbMock.user,"dropbox");
        Photo photo2 = new Photo("azeaze","salut", DbMock.user,"dropbox");

        DbMock.addPhoto(photo1);
        DbMock.addPhoto(photo2);
    }

    @Test
    public void addTest() throws PhotoAlreadyExistException{
        Photo photo1 = new Photo("salut","salut", DbMock.user,"dropbox");


        DbMock.addPhoto(photo1);

        assertTrue(DbMock.getPhotosFromEvent().get(0).equals(photo1));
    }
}
