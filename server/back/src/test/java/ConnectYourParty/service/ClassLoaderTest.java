package ConnectYourParty.service;

import ConnectYourParty.FileReader;
import ConnectYourParty.modulesLogic.service.ByteClassLoader;
import ConnectYourParty.services.IService;
import ConnectYourParty.services.photo.IPhotoService;
import org.junit.Before;
import org.junit.Test;

import java.beans.Transient;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
    public void wrongByteClassTest(){
        byte[] bin = new byte[10];

        Class serviceClass = this.loader.getClassFromByte(bin);

    }

}
