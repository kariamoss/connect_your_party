package connectYourParty.service;

import connectYourParty.businessObjects.service.Module;
import connectYourParty.exception.NoSuchModuleException;
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
