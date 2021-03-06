package connectYourParty.photo;

import connectYourParty.database.DbMock;
import connectYourParty.businessObjects.photo.Photo;
import connectYourParty.database.photo.IPhotoDatabase;
import connectYourParty.database.photo.PhotoDatabase;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.database.user.UserRegistry;
import connectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import connectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
public class PhotoInterpreterTest {

    @EJB
    private IPhotoInterpreter interpreter;

    @EJB
    private IPhotoInterpreter db;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(DbMock.class.getPackage())
                .addPackage(IPhotoDatabase.class.getPackage())
                .addPackage(PhotoDatabase.class.getPackage())
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoInterpreter.class.getPackage())
                .addPackage(IUserRegistry.class.getPackage())
                .addPackage(UserRegistry.class.getPackage());
    }

    @Test
    public void fallbackTest() throws Exception{
        InputStream stream = mock(InputStream.class);
        when(stream.available()).thenReturn(-1);

        Photo photo = new Photo("test","dominos");

        assertEquals(0,db.getPhotoList().size());
        this.interpreter.addPhoto(stream,photo.getName(),photo.getServiceHost(),DbMock.user.getName());

        assertEquals(0,db.getPhotoList().size());
    }
}
