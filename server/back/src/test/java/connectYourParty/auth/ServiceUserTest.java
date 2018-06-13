package connectYourParty.auth;

import connectYourParty.CotyPhotoService;
import connectYourParty.DropboxService;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.photo.IPhotoDatabase;
import connectYourParty.database.photo.PhotoDatabase;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.database.service.ServiceRegistry;
import connectYourParty.modulesLogic.common.interpreter.IInterpreter;
import connectYourParty.modulesLogic.common.interpreter.Interpreter;
import connectYourParty.modulesLogic.common.serviceUser.IServiceUser;
import connectYourParty.modulesLogic.common.serviceUser.ServiceUser;
import connectYourParty.modulesLogic.photo.serviceUser.IPhotoServiceUser;
import connectYourParty.modulesLogic.photo.serviceUser.PhotoServiceUser;
import connectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import connectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import connectYourParty.requestObjects.request.NullResponse;
import connectYourParty.requestObjects.request.OAuthHolder;
import connectYourParty.webInterface.IModule;
import connectYourParty.webInterface.Module;
import connectYourParty.webInterface.photo.IPhotoModule;
import connectYourParty.webInterface.photo.PhotoModule;
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
                new ServiceHolder(connectYourParty.businessObjects.service.Module.PHOTO, DropboxService.class));

        this.serviceRegistry.addServiceHolder(
                new ServiceHolder(connectYourParty.businessObjects.service.Module.PHOTO, CotyPhotoService.class));

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
