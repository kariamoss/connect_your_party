package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.modulesLogic.interpreter.PhotoInterpreter;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoInterpreterTest {
    private PhotoInterpreter interpreter;

    @Before
    public void init(){
        this.interpreter = new PhotoInterpreter();
    }

    @Test
    public void fallbackTest() throws Exception{
        InputStream stream = mock(InputStream.class);
        when(stream.available()).thenReturn(-1);

        Photo photo = new Photo("test","test",DbMock.user,"dominos");

        assertEquals(DbMock.getPhotosFromEvent().size(),0);

        this.interpreter.addPhoto(stream,photo.getName(),photo.getServiceHost());

        assertEquals(DbMock.getPhotosFromEvent().size(),0);
    }
}
