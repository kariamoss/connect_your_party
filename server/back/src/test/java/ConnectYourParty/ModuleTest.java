package ConnectYourParty;

import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.photo.PhotoDatabase;
import ConnectYourParty.database.token.ITokenDatabase;
import ConnectYourParty.database.token.TokenDatabase;
import ConnectYourParty.exception.NoSuchServiceException;
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
import cucumber.api.java.ca.I;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

@Ignore
@RunWith(Arquillian.class)
public class ModuleTest {

    @EJB IModule module;

    @EJB ITokenDatabase tokenDatabase;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IPhotoChooser.class.getPackage())
                .addPackage(PhotoChooser.class.getPackage())
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addPackage(PhotoInterpreter.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(IPhotoModule.class.getPackage())
                .addPackage(PhotoModule.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(PhotoServiceUser.class.getPackage())
                .addPackage(IPhotoDatabase.class.getPackage())
                .addPackage(IModule.class.getPackage())
                .addPackage(Module.class.getPackage())
                .addPackage(IInterpreter.class.getPackage())
                .addPackage(Interpreter.class.getPackage())
                .addPackage(IServiceUser.class.getPackage())
                .addPackage(ServiceUser.class.getPackage())
                .addPackage(ITokenDatabase.class.getPackage())
                .addPackage(TokenDatabase.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoDatabase.class.getPackage());
    }

    @Test
    public void sendOAuthTest() throws NoSuchServiceException {
        Response response = this.module.sendOAuthCode("code", "Dropbox");

    }
}
