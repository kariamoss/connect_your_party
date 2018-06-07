package ConnectYourParty.photo;

import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.photo.PhotoDatabase;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.database.service.ServiceRegistry;
import ConnectYourParty.modulesLogic.photo.ServiceUser.IPhotoServiceUser;
import ConnectYourParty.modulesLogic.photo.ServiceUser.PhotoServiceUser;
import ConnectYourParty.modulesLogic.photo.chooser.IPhotoChooser;
import ConnectYourParty.modulesLogic.photo.chooser.PhotoChooser;
import ConnectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import ConnectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertNotNull;


@RunWith(Arquillian.class)
public class PhotoInterpreterTest {

    @EJB
    private IPhotoInterpreter interpreter;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addPackage(IPhotoDatabase.class.getPackage())
                .addPackage(PhotoDatabase.class.getPackage())
                .addPackage(IPhotoChooser.class.getPackage())
                .addPackage(PhotoChooser.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(PhotoServiceUser.class.getPackage())
                .addPackage(IServiceRegistry.class.getPackage())
                .addPackage(ServiceRegistry.class.getPackage())
                .addPackage(PhotoDatabase.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoInterpreter.class.getPackage());
    }

    @Test
    public void getList(){
        assertNotNull(interpreter.getServices());
    }
}
