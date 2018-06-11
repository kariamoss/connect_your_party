package ConnectYourParty.user;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.photo.PhotoDatabase;
import ConnectYourParty.database.token.ITokenDatabase;
import ConnectYourParty.database.token.TokenDatabase;
import ConnectYourParty.database.user.IUserRegistry;
import ConnectYourParty.database.user.UserRegistry;
import ConnectYourParty.modulesLogic.common.interpreter.IInterpreter;
import ConnectYourParty.modulesLogic.common.interpreter.Interpreter;
import ConnectYourParty.modulesLogic.common.serviceUser.IServiceUser;
import ConnectYourParty.modulesLogic.common.serviceUser.ServiceUser;
import ConnectYourParty.modulesLogic.photo.ServiceUser.IPhotoServiceUser;
import ConnectYourParty.modulesLogic.photo.ServiceUser.PhotoServiceUser;
import ConnectYourParty.modulesLogic.photo.chooser.IPhotoChooser;
import ConnectYourParty.modulesLogic.photo.chooser.PhotoChooser;
import ConnectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import ConnectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import ConnectYourParty.webInterface.IModule;
import ConnectYourParty.webInterface.Module;
import ConnectYourParty.webInterface.photo.IPhotoModule;
import ConnectYourParty.webInterface.photo.PhotoModule;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserRegistryTest {

    @EJB
    private IUserRegistry registry;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(UserRegistry.class.getPackage())
                .addPackage(Music.class.getPackage())
                .addPackage(IUserRegistry.class.getPackage());
    }

    @Test
    public void listTest(){
        assertEquals(4,registry.getUserList().size());
    }

    @Test
    public void addTokenTest(){
        assertEquals(DbMock.user.getName(),
                this.registry.getUserById(DbMock.user.getName()).getName());
    }
}
