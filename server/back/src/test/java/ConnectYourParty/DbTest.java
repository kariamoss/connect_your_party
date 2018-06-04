package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class DbTest{

    @EJB
    DbMock db;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(DbMock.class.getPackage());
    }

    @Before
    @After
    public void init(){
        db.clean();
    }

    @Test(expected = PhotoAlreadyExistException.class)
    public void doublePhoto() throws PhotoAlreadyExistException{
        Photo photo1 = new Photo("salut","salut", db.getUser(),"dropbox");
        Photo photo2 = new Photo("salut","ezaeazezae", db.getUser(),"dropbox");

        db.addPhoto(db.getEvent(), photo1);
        db.addPhoto(db.getEvent(), photo2);
    }

    @Test
    public void addTest() throws PhotoAlreadyExistException{
        Photo photo1 = new Photo("salut","salut", db.getUser(),"dropbox");


        db.addPhoto(db.getEvent(), photo1);

        assertTrue(db.getPhotosFromEvent().get(0).equals(photo1));
    }

    @Test
    public void photoEqualTest(){
        Photo photo1 = new Photo("salut","salut", db.getUser(),"dropbox");
        assertTrue(photo1.equals(photo1));
    }

    @Test
    public void photoNotEqualTest(){
        Photo photo1 = new Photo("salut","salut", db.getUser(),"dropbox");
        Photo photo2 = new Photo("azeaze","salut", db.getUser(),"dropbox");

        assertFalse(photo1.equals(photo2));
    }

    @Test
    public void serviceRetrievalTest() throws Exception{
        String path = "salut";
        String service = "Dropbox";
        Photo photo1 = new Photo(path,"salut", db.getUser(),service);
        db.addPhoto(db.getEvent(), photo1);

        assertEquals(service, db.getServiceFromPath(path));
    }

    @Test(expected = NoSuchPhotoException.class)
    public void serviceRetrievalExceptionTest() throws Exception{
        String path = "salut";
        Photo photo1 = new Photo(path,"salut", db.getUser(),"drop");

        db.addPhoto(db.getEvent(), photo1);

        db.getServiceFromPath("aaaaa");
    }

    @Test
    public void removeTest() throws Exception{
        Photo photo1 = new Photo("salut","salut", db.getUser(),"dropbox");
        db.addPhoto(db.getEvent(),photo1);
        assertEquals(db.getPhotosFromEvent().size(),1);

        db.removePhotoFromEvent(db.getEvent(),photo1);
        assertEquals(db.getPhotosFromEvent().size(),0);

    }
}
