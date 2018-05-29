package ConnectYourParty;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import org.junit.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ServiceTest {
    private static DropboxService service;
    private String destPath = "/test/imageTests.jpg";

    @BeforeClass
    public static void init(){
        service = new DropboxService();
    }

    @Before
    @After
    public void removal(){
        service.remove(this.destPath);
    }

    @Test
    public void addAndGetTest() throws Exception{
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        InputStream in = new FileInputStream(path.getPath());
        byte[] buff = new byte[in.available()];
        in.read(buff);

        service.addPhoto(buff,destPath);

        byte[] recv = this.service.getPhoto(destPath);

        assertTrue(Arrays.equals(buff,recv));
    }

    @Test
    public void removeTest() throws AddPhotoErrorException {
        byte[] buff = new byte[3];
        buff[0] = 1;
        service.addPhoto(buff,this.destPath); // No exception

        assertTrue(service.remove(this.destPath));

        service.addPhoto(buff,this.destPath); // No exception

    }


}
