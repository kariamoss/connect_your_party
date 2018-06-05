package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import ConnectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
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
    IPhotoInterpreter interpreter;

    @EJB
    DbMock db;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(DbMock.class.getPackage())
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addPackage(PhotoInterpreter.class.getPackage());
    }

    @Test
    public void fallbackTest() throws Exception{
        InputStream stream = mock(InputStream.class);
        when(stream.available()).thenReturn(-1);

        Photo photo = new Photo("test","test",db.getUser(),"dominos");

        assertEquals(db.getPhotosFromEvent().size(),0);
        this.interpreter.addPhoto(stream,photo.getName(),photo.getServiceHost());

        assertEquals(db.getPhotosFromEvent().size(),0);
    }
}
