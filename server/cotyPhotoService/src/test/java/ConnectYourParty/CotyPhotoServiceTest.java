package ConnectYourParty;

import static org.junit.Assert.assertTrue;

import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;


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

        byte[] recv = this.service.getPhoto(destPath);

        assertTrue(Arrays.equals(buff,recv));
    }

    @Test(expected = RetrievePhotoErrorException.class)
    public void retrieveUnknownPhoto() throws RetrievePhotoErrorException {
        byte[] recv = this.service.getPhoto("salut");
    }
}
