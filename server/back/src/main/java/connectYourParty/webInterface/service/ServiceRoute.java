package connectYourParty.webInterface.service;

import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.requestObjects.request.ServiceAdderBody;
import connectYourParty.webInterface.CorsAdder;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class ServiceRoute implements IServiceRoute{

    Logger logger = Logger.getLogger(ServiceRoute.class.getName());

    @EJB
    private IServiceRegistry serviceRegistry;

    @Override
    public Response addService(MultipartBody body) {
        try {
            ServiceAdderBody serviceBody = parseBody(body);

            byte[] bin = new byte[serviceBody.getFile().available()];
            Module currModule = Module.getModule(serviceBody.getModule());
            serviceBody.getFile().read(bin);

            ServiceHolder serviceHolder = new ServiceHolder(currModule,bin);

            serviceRegistry.addServiceHolder(serviceHolder);

            logger.log(Level.INFO,"class added");

            return CorsAdder.corsResponse().build();
        } catch (Exception e){
            e.printStackTrace();
            logger.log(Level.WARNING,e.getMessage());
            return CorsAdder.corsResponse().status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    private ServiceAdderBody parseBody(MultipartBody body) throws IOException {
        ServiceAdderBody service = new ServiceAdderBody();
        service.setName(body.getAttachmentObject("name",String.class));
        service.setFile( body.getAttachment("file").getDataHandler().getInputStream());
        service.setModule( body.getAttachmentObject("module",String.class));

        if(!service.check()){
            service.setName(body.getAttachment("name").getDataHandler().getContent().toString());
            service.setModule(body.getAttachment("service").getDataHandler().getContent().toString());
        }

        logger.log(Level.INFO,"module:" + service.getModule());

        return service;
    }


}
