package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.modulesLogic.interpreter.IPhotoInterpreter;
import ConnectYourParty.modulesLogic.interpreter.PhotoInterpreter;
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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

    @EJB DbMock db;

    @EJB
    IPhotoInterpreter photoInterpreter;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(DbMock.class.getPackage())
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addPackage(PhotoInterpreter.class.getPackage());
    }

    @Before
    @After
    public void init(){
        db.clean();
    }

    @Test
    public void addPhotoAndGetList() throws PhotoAlreadyExistException {
        db.addPhoto(db.getEvent(), new Photo("salut", "name", new User("milleret", "jehan"), "Dropbox"));

        List<PhotoHolder> photoHolderList = photoInterpreter.getPhotoList();

        Assert.assertEquals(1, photoHolderList.size());

        PhotoHolder photoHolder = photoHolderList.get(0);

        Assert.assertEquals("salut/name", photoHolder.photoPath);
        Assert.assertEquals("name", photoHolder.name);
        Assert.assertEquals("jehan milleret", photoHolder.user);
    }
}
