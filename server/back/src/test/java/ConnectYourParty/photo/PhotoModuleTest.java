package ConnectYourParty.photo;

import ConnectYourParty.CotyPhotoService;
import ConnectYourParty.DropboxService;
import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.photo.PhotoDatabase;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.database.service.ServiceRegistry;
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
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import ConnectYourParty.webInterface.IModule;
import ConnectYourParty.webInterface.Module;
import ConnectYourParty.webInterface.photo.IPhotoModule;
import ConnectYourParty.webInterface.photo.PhotoModule;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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



    @EJB
    IPhotoModule module;

    @EJB
    IUserRegistry userRegistry;

    @EJB private IServiceRegistry servRegistry;

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
                .addPackage(IUserRegistry.class.getPackage())
                .addPackage(UserRegistry.class.getPackage())
                .addPackage(IServiceRegistry.class.getPackage())
                .addPackage(ServiceRegistry.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(PhotoDatabase.class.getPackage());
    }

    @Test
    public void addAndGetTest() throws Exception{
        String imagePath = "test/test.jpg";

        servRegistry.addServiceHolder(new ServiceHolder(ConnectYourParty.businessObjects.service.Module.PHOTO, CotyPhotoService.class));

        URL path = this.getClass().getClassLoader().getResource("image.jpg");

        DataHandler nameHandler = new DataHandler(imagePath,"text/plain");
        DataHandler userIdHandler = new DataHandler(DbMock.user.getName(),"text/plain");
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

}
