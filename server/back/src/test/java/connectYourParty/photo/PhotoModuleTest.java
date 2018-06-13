package connectYourParty.photo;

import connectYourParty.CotyPhotoService;
import connectYourParty.DropboxService;
import connectYourParty.SpotifyService;
import connectYourParty.businessObjects.Token;
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
import connectYourParty.modulesLogic.photo.ServiceUser.IPhotoServiceUser;
import connectYourParty.modulesLogic.photo.ServiceUser.PhotoServiceUser;
import connectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import connectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import connectYourParty.requestObjects.photo.PhotoHolder;
import connectYourParty.webInterface.IModule;
import connectYourParty.webInterface.Module;
import connectYourParty.webInterface.photo.IPhotoModule;
import connectYourParty.webInterface.photo.PhotoModule;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RunWith(Arquillian.class)
public class PhotoModuleTest {

    private String tokenDrop = "3R_uMjczZjAAAAAAAAAAfB2FMQjheEyR89fJsWHUv7pVSI-yV1ai3w4FlsK5M9fP";

    @EJB
    IPhotoModule module;

    @EJB
    IUserRegistry userRegistry;

    @EJB private IPhotoDatabase photoDatabase;

    @EJB private IServiceRegistry servRegistry;

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
                .addPackage(IUserRegistry.class.getPackage())
                .addPackage(UserRegistry.class.getPackage())
                .addPackage(IServiceRegistry.class.getPackage())
                .addPackage(ServiceRegistry.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoDatabase.class.getPackage());
    }

    @Before
    public void dbClean(){
        this.servRegistry.clean();
        this.photoDatabase.clean();
    }

    @Test
    public void addAndGetTest() throws Exception{
        String imagePath = "test/test.jpg";

        servRegistry.addServiceHolder(new ServiceHolder(connectYourParty.businessObjects.service.Module.PHOTO, CotyPhotoService.class));

        URL path = this.getClass().getClassLoader().getResource("image.jpg");

        DataHandler nameHandler = new DataHandler(imagePath,"text/plain");
        DataHandler userIdHandler = new DataHandler(DbMock.user2.getName(),"text/plain");
        DataHandler serviceHandler = new DataHandler(new CotyPhotoService().getServiceName(),"text/plain");

        MultivaluedHashMap header = new MultivaluedHashMap<String,String>();


        Attachment name = new Attachment("name",nameHandler,header);
        Attachment userId = new Attachment("userId",userIdHandler,header);
        Attachment service = new Attachment("service", serviceHandler,header);
        Attachment file = new Attachment("file",new FileInputStream(path.getPath()),null);

        MultipartBody body = new MultipartBody(Arrays.asList(name,
                service,
                file,
                userId
        ));

        Response responseAdd = this.module.addPhoto(body);

        assertEquals(responseAdd.getStatus(),200);

        //assertTrue(db.getPhotosFromEvent().contains(new Photo(imagePath,"test",db.getUser(),coty.getServiceName())));

        Response response = this.module.getPhotoList();
        List<PhotoHolder> holder = (List<PhotoHolder>) response.getEntity();
        assertEquals(1,holder.size());

        String event = holder.get(0).photoPath.split("/")[0];
        String namePhoto = holder.get(0).photoPath.split("/")[1];

        Response responseGet = this.module.getPhoto(event, namePhoto);

        assertEquals(responseGet.getStatus(),200);

        byte[] arr = (byte[])responseGet.getEntity();

        InputStream input = new FileInputStream(path.getPath());
        byte[] buff = new byte[input.available()];
        input.read(buff);

        assertTrue(Arrays.equals(arr,buff));
    }

    @Test
    public void addAndGetWithDropBoxTest() throws Exception{
        String imagePath = "test/test.jpg";

        servRegistry.addServiceHolder(new ServiceHolder(connectYourParty.businessObjects.service.Module.PHOTO, DropboxService.class));

        URL path = this.getClass().getClassLoader().getResource("image.jpg");

        User user = this.userRegistry.getUserById(DbMock.user2.getName());
        this.userRegistry.addToken(user,new Token("code","Dropbox",tokenDrop));

        DataHandler nameHandler = new DataHandler(imagePath,"text/plain");
        DataHandler userIdHandler = new DataHandler(user.getName(),"text/plain");
        DataHandler serviceHandler = new DataHandler(new DropboxService().getServiceName(),"text/plain");

        MultivaluedHashMap header = new MultivaluedHashMap<String,String>();


        Attachment name = new Attachment("name",nameHandler,header);
        Attachment userId = new Attachment("userId",userIdHandler,header);
        Attachment service = new Attachment("service", serviceHandler,header);
        Attachment file = new Attachment("file",new FileInputStream(path.getPath()),null);

        MultipartBody body = new MultipartBody(Arrays.asList(name,
                service,
                file,
                userId
        ));
        int nbPhoto = this.photoDatabase.getPhotoList().size();
        Response responseAdd = this.module.addPhoto(body);

        assertEquals(responseAdd.getStatus(),200);

        //assertTrue(db.getPhotosFromEvent().contains(new Photo(imagePath,"test",db.getUser(),coty.getServiceName())));

        Response response = this.module.getPhotoList();
        List<PhotoHolder> holder = (List<PhotoHolder>) response.getEntity();
        assertEquals(nbPhoto+1,holder.size());

        String event = holder.get(0).photoPath.split("/")[0];
        String namePhoto = holder.get(0).photoPath.split("/")[1];

        Response responseGet = this.module.getPhoto(event, namePhoto);

        assertEquals(responseGet.getStatus(),200);

        byte[] arr = (byte[])responseGet.getEntity();

        InputStream input = new FileInputStream(path.getPath());
        byte[] buff = new byte[input.available()];
        input.read(buff);

        assertTrue(Arrays.equals(arr,buff));
    }

    @Test
    public void addingOtherModuleService(){
        this.servRegistry.addServiceHolder(new ServiceHolder(
                connectYourParty.businessObjects.service.Module.MUSIC, SpotifyService.class
        ));

        Response serviceList = this.module.getPhotoServices();

        List<ServiceHolder> services = (List<ServiceHolder>) serviceList.getEntity();

        assertEquals(0,services.size());
    }

}
