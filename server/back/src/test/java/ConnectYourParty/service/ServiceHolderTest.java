package ConnectYourParty.service;

import ConnectYourParty.FileReader;
import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.services.IService;
import ConnectYourParty.services.photo.IPhotoService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServiceHolderTest {

    @Test
    public void loadTest() throws Exception{
        ServiceHolder serv = new ServiceHolder(Module.PHOTO, FileReader.readfile());

        IService service = serv.getService();

        assertEquals("Dropbox",service.getServiceName());

        Module.PHOTO.getModClass().cast(service); //should not throw exception
    }
}
