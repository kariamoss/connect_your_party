package ConnectYourParty;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import org.junit.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

public class DropboxTest {
    private static DropboxService service;
    private String destPath = "/test/imageTests.jpg";

    @BeforeClass
    public static void init(){
        service = new DropboxService();
    }

    @Before
    @After
    public void removal() {
        try {
            service.removePhoto(this.destPath, );
        } catch (CannotDeletePhotoException e) {
            // no problem
        }
    }

    @Test
    public void addAndGetTest() throws Exception{
        URL path = this.getClass().getClassLoader().getResource("grosseImage.jpg");
        InputStream in = new FileInputStream(path.getPath());
        byte[] buff = new byte[in.available()];
        in.read(buff);

        service.addPhoto(buff,destPath, );

        byte[] recv = this.service.getPhoto(destPath, );

        assertTrue(Arrays.equals(buff,recv));
    }

    @Test
    public void removeTest() throws AddPhotoErrorException, CannotDeletePhotoException {
        byte[] buff = new byte[3];
        buff[0] = 1;
        service.addPhoto(buff,this.destPath, ); // No exception

        service.removePhoto(this.destPath, ); //No exception

        service.addPhoto(buff,this.destPath, ); // No exception

    }

    @Test
    public void UrlTest(){
        assertNotNull(service.getServiceIcon());
    }


}
