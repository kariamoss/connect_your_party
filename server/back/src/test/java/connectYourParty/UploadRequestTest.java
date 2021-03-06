package connectYourParty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import connectYourParty.requestObjects.photo.UploadRequest;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class UploadRequestTest
{
    @Test
    public void checkTest()
    {
        UploadRequest photo = new UploadRequest();
        assertFalse(photo.check());
    }

    @Test
    public void oneElementTest(){
        UploadRequest photo = new UploadRequest();
        photo.name = "salut";
        assertFalse(photo.check());
    }

    @Test
    public void allElemTest(){
        UploadRequest photo = new UploadRequest();
        photo.name = "";
        photo.format = "";
        photo.serviceName ="";
        photo.photo = "";

        assertTrue(photo.check());
    }
}
