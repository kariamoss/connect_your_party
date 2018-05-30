package ConnectYourParty;

import static org.junit.Assert.assertTrue;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
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

    @Before
    @After
    public void removePhoto(){
        try {
            service.removePhoto("image.jpg");
        } catch (CannotDeletePhotoException e) {

        }
    }

    @Test
    public void addPhotoTest() throws Exception {
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        InputStream in = new FileInputStream(path.getPath());
        byte[] buff = new byte[in.available()];
        in.read(buff);

        service.addPhoto(buff,destPath);

        byte[] recv = service.getPhoto(destPath);

        assertTrue(Arrays.equals(buff,recv));
    }

    @Test(expected = RetrievePhotoErrorException.class)
    public void retrieveUnknownPhoto() throws RetrievePhotoErrorException {
        byte[] recv = service.getPhoto("salut");
    }

    @Test(expected = CannotDeletePhotoException.class)
    public void deletePhotoExceptionTest() throws CannotDeletePhotoException {
        service.removePhoto("salut");
    }

    @Test
    public void deletePhotoTest() throws IOException, AddPhotoErrorException, CannotDeletePhotoException {
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        InputStream in = new FileInputStream(path.getPath());
        byte[] buff = new byte[in.available()];
        in.read(buff);

        service.addPhoto(buff,destPath);

        service.removePhoto(destPath);
    }
}
