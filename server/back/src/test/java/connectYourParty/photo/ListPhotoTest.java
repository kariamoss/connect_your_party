package connectYourParty.photo;

import connectYourParty.database.DbMock;
import connectYourParty.businessObjects.photo.Photo;
import connectYourParty.database.photo.IPhotoDatabase;
import connectYourParty.database.photo.PhotoDatabase;
import connectYourParty.exception.photo.PhotoAlreadyExistException;
import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import connectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import connectYourParty.requestObjects.photo.PhotoHolder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

@RunWith(Arquillian.class)
public class ListPhotoTest {

    @EJB
    IPhotoDatabase db;

    @EJB
    IPhotoInterpreter photoInterpreter;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IPhotoDatabase.class.getPackage())
                .addPackage(PhotoDatabase.class.getPackage())
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoInterpreter.class.getPackage());
    }

    @Before
    @After
    public void init(){
        db.clean();
    }

    @Test
    public void addPhotoAndGetList() throws PhotoAlreadyExistException,AddPhotoErrorException {
        db.addPhoto(new Photo( "name", "Dropbox"));

        List<PhotoHolder> photoHolderList = photoInterpreter.getPhotoList();

        Assert.assertEquals(1, photoHolderList.size());

        PhotoHolder photoHolder = photoHolderList.get(0);

        Assert.assertEquals(DbMock.event.getId() + "/name", photoHolder.photoPath);
        Assert.assertEquals("name", photoHolder.name);
        Assert.assertEquals("Jehan", photoHolder.user);
    }
}
