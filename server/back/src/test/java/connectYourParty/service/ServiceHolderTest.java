package connectYourParty.service;

import connectYourParty.FileReader;
import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.exception.WrongClassBinaryexception;
import connectYourParty.services.IService;
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

    @Test(expected = WrongClassBinaryexception.class)
    public void wrongBinException() throws Exception{
        new ServiceHolder(Module.PHOTO, new byte[20]);
    }
}
