package ConnectYourParty.webInterface.service;

import ConnectYourParty.webInterface.CorsAdder;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceRoute implements IServiceRoute{

    Logger logger = Logger.getLogger(ServiceRoute.class.getName());


    @Override
    public Response addService(MultipartBody body) {
        try {
            String name = body.getAttachmentObject("name", String.class);
            String module = body.getAttachmentObject("module", String.class);
            InputStream file = body.getAttachment("file").getDataHandler().getInputStream();
            

            return CorsAdder.corsResponse().build();
        } catch (Exception e){
            e.printStackTrace();
            logger.log(Level.WARNING,e.getMessage());
            return CorsAdder.corsResponse().status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }


}
