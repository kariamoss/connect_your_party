package connectYourParty;

import static org.junit.Assert.assertTrue;

import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.exceptions.photo.RetrievePhotoErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;


public class CotyPhotoServiceTest
{
    private static CotyPhotoService service;
    private String destPath = "/test/test/imageTests.jpg";

    @BeforeClass
    public static void init(){
        service = new CotyPhotoService();
    }

    @Before
    @After
    public void removePhoto(){
        try {
            service.removePhoto("image.jpg", Optional.empty());
        } catch (CannotDeletePhotoException e) {

        }
    }

    @Test
    public void addPhotoTest() throws Exception {
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        InputStream in = new FileInputStream(path.getPath());
        byte[] buff = new byte[in.available()];
        in.read(buff);

        service.addPhoto(buff,destPath, Optional.empty());

        byte[] recv = service.getPhoto(destPath, Optional.empty());

        assertTrue(Arrays.equals(buff,recv));
    }

    @Test(expected = RetrievePhotoErrorException.class)
    public void retrieveUnknownPhoto() throws RetrievePhotoErrorException {
        byte[] recv = service.getPhoto("salut", Optional.empty());
    }

    @Test(expected = CannotDeletePhotoException.class)
    public void deletePhotoExceptionTest() throws CannotDeletePhotoException {
        service.removePhoto("salut", Optional.empty());
    }

    @Test
    public void deletePhotoTest() throws IOException, AddPhotoErrorException, CannotDeletePhotoException {
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        InputStream in = new FileInputStream(path.getPath());
        byte[] buff = new byte[in.available()];
        in.read(buff);

        service.addPhoto(buff,destPath, Optional.empty());

        service.removePhoto(destPath, Optional.empty());
    }
}
