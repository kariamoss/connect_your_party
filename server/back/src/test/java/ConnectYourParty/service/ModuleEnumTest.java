package ConnectYourParty.service;

import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.exception.NoSuchModuleException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModuleEnumTest {
    private String music = "music";
    private String photo = "photo";

    @Test
    public void getMusicModuleTest() throws Exception{
        assertEquals(Module.getModule(music),Module.MUSIC);
    }


    @Test
    public void getPhotoModuleTest() throws Exception{
        assertEquals(Module.getModule(photo),Module.PHOTO);
    }

    @Test(expected = NoSuchModuleException.class)
    public void getInexistantModuleTest() throws Exception{
        Module.getModule("aezaeae");
    }
}
