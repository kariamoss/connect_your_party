package ConnectYourParty.photo;

import ConnectYourParty.businessObjects.User;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.photo.PhotoDatabase;
import ConnectYourParty.exception.photo.NoSuchPhotoException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Arquillian.class)
public class DbTest {

    private User user = new User("salut","salut");

    @EJB private IPhotoDatabase db;

    @Before
    public void clean(){
        this.db.clean();
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IPhotoDatabase.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoDatabase.class.getPackage());
    }

    @Test
    public void addTest() throws Exception{
        Photo photo = new Photo(
                "test",
                "Dropbox");

        db.addPhoto(photo);
        List<Photo> photos = db.getPhotoList();
        assertEquals(photos.get(0),photo);
    }

    //-javaagent:server/agent/openejb-javaagent-4.7.5.jar
    @Test
    public void emptyListTest(){
        assertEquals(db.getPhotoList().size(),0);
    }

    @Test
    public void removeTest() throws Exception{
        Photo photo = new Photo(
                "test",
                "Dropbox");

        db.addPhoto(photo);
        List<Photo> photos = db.getPhotoList();
        assertEquals(photos.get(0),photo);

        db.removePhoto(photo);

        assertEquals(0,db.getPhotoList().size());

    }

    @Test(expected = AddPhotoErrorException.class)
    public void doublePhoto() throws AddPhotoErrorException{
        Photo photo1 = new Photo("salut","dropbox");
        Photo photo2 = new Photo("salut","dropbox");

        db.addPhoto(photo1);
        db.addPhoto(photo2);
    }

    @Test
    public void photoRetrievalTest() throws Exception{
        String path = "salut";
        String service = "Dropbox";
        Photo photo1 = new Photo(path, service);
        db.addPhoto(photo1);

        assertEquals(photo1, db.getPhotoFromPath(DbMock.event.getId()+"/"+path));
    }

    @Test(expected = NoSuchPhotoException.class)
    public void photoFailRetrievalTest() throws Exception{
        String path = "salut";
        String service = "Dropbox";
        Photo photo1 = new Photo(path,service);
        db.addPhoto(photo1);

        db.getPhotoFromPath("fff");
    }

    @Test
    public void addingMultiplePhotoTest() throws Exception{
        Photo photo1 = new Photo("salut","Drop");
        Photo photo2 = new Photo("azea","Drop");
        Photo photo3 = new Photo("azeaze","Drop");
        Photo photo4 = new Photo("azeaz","Drop");

        this.db.addPhoto(photo1);
        this.db.addPhoto(photo2);
        this.db.addPhoto(photo3);
        this.db.addPhoto(photo4);

        assertEquals(4,this.db.getPhotoList().size());

    }



}
