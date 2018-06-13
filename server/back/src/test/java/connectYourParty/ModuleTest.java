package connectYourParty;

import connectYourParty.businessObjects.User;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.DbMock;
import connectYourParty.database.photo.IPhotoDatabase;
import connectYourParty.database.photo.PhotoDatabase;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.database.service.ServiceRegistry;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.database.user.UserRegistry;
import connectYourParty.modulesLogic.common.interpreter.IInterpreter;
import connectYourParty.modulesLogic.common.interpreter.Interpreter;
import connectYourParty.modulesLogic.common.serviceUser.IServiceUser;
import connectYourParty.modulesLogic.common.serviceUser.ServiceUser;
import connectYourParty.modulesLogic.photo.serviceUser.IPhotoServiceUser;
import connectYourParty.modulesLogic.photo.serviceUser.PhotoServiceUser;
import connectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import connectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;



@Ignore
@RunWith(Arquillian.class)
public class ModuleTest {

    @EJB IModule module;

    @EJB IUserRegistry userRegistry;

    @EJB private IServiceRegistry serviceRegistry;

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
                .addPackage(IServiceRegistry.class.getPackage())
                .addPackage(ServiceRegistry.class.getPackage())
                .addPackage(ServiceUser.class.getPackage())
                .addPackage(IUserRegistry.class.getPackage())
                .addPackage(UserRegistry.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoDatabase.class.getPackage());
    }

    @Test
    public void sendOAuthTest() throws Exception {

        this.serviceRegistry.addServiceHolder(new ServiceHolder(connectYourParty.businessObjects.service.Module.PHOTO,
                DropboxService.class));

        Response response = this.module.sendOAuthCode("code", "Dropbox", DbMock.user.getName());

        assertEquals(200,response.getStatus());

        User user = this.userRegistry.getUserById(DbMock.user.getName());

        assertNotEquals(0,user.getTokenList().size());
    }


}
