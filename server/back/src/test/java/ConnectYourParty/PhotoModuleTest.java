package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import ConnectYourParty.webInterface.photo.PhotoModule;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


import javax.activation.DataHandler;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


public class PhotoModuleTest {

    private PhotoModule module;

    @Before
    public void init(){
        this.module = new PhotoModule();
    }

    @Test
    public void addAndGetTest() throws Exception{
        String imagePath = "test/test/test.jpg";

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

        DbMock.getPhotosFromEvent().contains(new Photo(imagePath,"test",DbMock.user,coty.getServiceName()));

        Response response = module.getPhotoList();
        List<PhotoHolder> holder = (List<PhotoHolder>) response.getEntity();
        assertEquals(holder.size(),1);

        Response responseGet = this.module.getPhoto(holder.get(0).photoPath);

        assertEquals(responseGet.getStatus(),200);

        byte[] arr = (byte[])responseGet.getEntity();

        InputStream input = new FileInputStream(path.getPath());
        byte[] buff = new byte[input.available()];
        input.read(buff);

        assertTrue(Arrays.equals(arr,buff));
    }

}
