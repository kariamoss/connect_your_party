package connectYourParty.service;

import connectYourParty.FileReader;
import connectYourParty.modulesLogic.service.ByteClassLoader;
import connectYourParty.services.IService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClassLoaderTest {
    private ByteClassLoader loader;
    private String serviceName = "Dropbox";


    @Before
    public void init(){
        loader = new ByteClassLoader(this.getClass().getClassLoader());
    }

    @Test
    public void loadClass() throws Exception{
        byte[] bin = FileReader.readfile();

        Class serviceClass = this.loader.getClassFromByte(bin);
        assertNotNull(serviceClass);

        IService service = (IService)serviceClass.newInstance();
        assertEquals(serviceName,service.getServiceName());
    }

    @Test(expected = ClassFormatError.class)
    public void wrongByteClassTest() throws Exception{
        byte[] bin = new byte[10];

        this.loader.getClassFromByte(bin);

    }

}
