package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class DbTest{
    @Before
    public void init(){
        DbMock.clean();
    }

    @Test(expected = PhotoAlreadyExistException.class)
    public void doublePhoto() throws PhotoAlreadyExistException{
        Photo photo1 = new Photo("salut","salut", DbMock.user,"dropbox");
        Photo photo2 = new Photo("salut","ezaeazezae", DbMock.user,"dropbox");

        DbMock.addPhoto(photo1);
        DbMock.addPhoto(photo2);
    }

    @Test
    public void addTest() throws PhotoAlreadyExistException{
        Photo photo1 = new Photo("salut","salut", DbMock.user,"dropbox");


        DbMock.addPhoto(photo1);

        assertTrue(DbMock.getPhotosFromEvent().get(0).equals(photo1));
    }

    @Test
    public void photoEqualTest(){
        Photo photo1 = new Photo("salut","salut", DbMock.user,"dropbox");
        assertTrue(photo1.equals(photo1));
    }

    @Test
    public void photoNotEqualTest(){
        Photo photo1 = new Photo("salut","salut", DbMock.user,"dropbox");
        Photo photo2 = new Photo("azeaze","salut", DbMock.user,"dropbox");

        assertFalse(photo1.equals(photo2));
    }

    @Test
    public void serviceRetrievalTest() throws Exception{
        String path = "salut";
        String service = "dropbox";
        Photo photo1 = new Photo(path,"salut", DbMock.user,service);

        DbMock.addPhoto(photo1);

        assertEquals(DbMock.getServiceFromPath(path),service);
    }

    @Test(expected = NoSuchPhotoException.class)
    public void serviceRetrievalExceptionTest() throws Exception{
        String path = "salut";
        Photo photo1 = new Photo(path,"salut", DbMock.user,"drop");

        DbMock.addPhoto(photo1);

        DbMock.getServiceFromPath("aaaaa");
    }
}
