package ConnectYourParty;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class CotyPhotoServiceTest
{
    private static CotyPhotoService service;
    private String destPath = "/test/imageTests.jpg";

    @BeforeClass
    public static void init(){
        service = new CotyPhotoService();
    }

    @Test
    public void addPhotoTest() throws Exception {
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        InputStream in = new FileInputStream(path.getPath());
        byte[] buff = new byte[in.available()];
        in.read(buff);

        service.addPhoto(buff,destPath);
    }
}
