package connectYourParty;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.objects.TokenService;
import org.junit.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

public class DropboxTest {
    private static DropboxService service;
    private String destPath = "/test/imageTests.jpg";
    private final String token = "3R_uMjczZjAAAAAAAAAAfB2FMQjheEyR89fJsWHUv7pVSI-yV1ai3w4FlsK5M9fP";

    @BeforeClass
    public static void init(){
        service = new DropboxService();
    }

    @Before
    @After
    public void removal() {
        try {
            service.removePhoto(this.destPath, Optional.of(new TokenService("code", token, null)));
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

        service.addPhoto(buff,destPath, Optional.of(new TokenService("code", token, null)));

        byte[] recv = this.service.getPhoto(destPath, Optional.of(new TokenService("code", token, null)));

        assertTrue(Arrays.equals(buff,recv));
    }

    @Test
    public void removeTest() throws AddPhotoErrorException, CannotDeletePhotoException {
        byte[] buff = new byte[3];
        buff[0] = 1;
        service.addPhoto(buff,this.destPath, Optional.of(new TokenService("code", token, null))); // No exception

        service.removePhoto(this.destPath, Optional.of(new TokenService("code", token, null))); //No exception

        service.addPhoto(buff,this.destPath, Optional.of(new TokenService("code", token, null))); // No exception

    }

    @Test
    public void UrlTest(){
        assertNotNull(service.getServiceIcon());
    }

    @Test
    public void OAuthTest(){
        assertNotNull(service.getOAuthUrl());
    }


}
