package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.businessObjects.Photo;
import ConnectYourParty.modulesLogic.ServiceUser.IPhotoServiceUser;
import ConnectYourParty.modulesLogic.ServiceUser.PhotoServiceUser;
import ConnectYourParty.modulesLogic.chooser.IPhotoChooser;
import ConnectYourParty.modulesLogic.chooser.PhotoChooser;
import ConnectYourParty.modulesLogic.interpreter.IPhotoInterpreter;
import ConnectYourParty.modulesLogic.interpreter.PhotoInterpreter;
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import ConnectYourParty.services.photo.IPhotoService;
import ConnectYourParty.webInterface.photo.IPhotoModule;
import ConnectYourParty.webInterface.photo.PhotoModule;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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


    @EJB DbMock db;

    @EJB
    IPhotoModule module;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(DbMock.class.getPackage())
                .addPackage(IPhotoChooser.class.getPackage())
                .addPackage(PhotoChooser.class.getPackage())
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addPackage(PhotoInterpreter.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(IPhotoModule.class.getPackage())
                .addPackage(PhotoModule.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(PhotoServiceUser.class.getPackage());
    }

    @Test
    public void addAndGetTest() throws Exception{
        String imagePath = "test/test.jpg";

        CotyPhotoService coty = new CotyPhotoService();
        URL path = this.getClass().getClassLoader().getResource("image.jpg");

        DataHandler nameHandler = new DataHandler(imagePath,"text/plain");
        DataHandler serviceHandler = new DataHandler("Dropbox","text/plain");

        MultivaluedHashMap header = new MultivaluedHashMap<String,String>();


        Attachment name = new Attachment("name",nameHandler,header);
        Attachment service = new Attachment("service", serviceHandler,header);
        Attachment file = new Attachment("file",new FileInputStream(path.getPath()),null);

        MultipartBody body = new MultipartBody(Arrays.asList(name,
                service,
                file
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
