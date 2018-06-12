package ConnectYourParty.auth;

import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.photo.PhotoDatabase;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.database.service.ServiceRegistry;
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
import ConnectYourParty.requestObjects.request.NullResponse;
import ConnectYourParty.requestObjects.request.OAuthHolder;
import ConnectYourParty.webInterface.IModule;
import ConnectYourParty.webInterface.Module;
import ConnectYourParty.webInterface.photo.IPhotoModule;
import ConnectYourParty.webInterface.photo.PhotoModule;
import org.bouncycastle.math.raw.Mod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class ServiceUserTest {

    @EJB
    private IServiceRegistry serviceRegistry;

    @EJB private IModule module;

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
                .addPackage(IModule.class.getPackage())
                .addPackage(Module.class.getPackage())
                .addPackage(IServiceRegistry.class.getPackage())
                .addPackage(ServiceRegistry.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoDatabase.class.getPackage());
    }

    @Test
    public void retrieveOAuth() throws Exception{
        this.serviceRegistry.addServiceHolder(
                new ServiceHolder(ConnectYourParty.businessObjects.service.Module.PHOTO, DropboxService.class));

        this.serviceRegistry.addServiceHolder(
                new ServiceHolder(ConnectYourParty.businessObjects.service.Module.PHOTO, CotyPhotoService.class));

        Response response = this.module.retrieveOAuthURL("Dropbox");

        assertEquals(200,response.getStatus());

        OAuthHolder holder = (OAuthHolder)response.getEntity();
        DropboxService drop = new DropboxService();

        assertEquals(holder.getClient_id(),drop.getAppKey());
        assertEquals(holder.getOAuthURL(),drop.getOAuthUrl().toString());

    }

    @Test
    public void falseService() throws Exception{
        Response response = this.module.retrieveOAuthURL("azeazeaze");

        assertEquals(200,response.getStatus());


        assertTrue(response.getEntity() instanceof NullResponse);

    }
}
