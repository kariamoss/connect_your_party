package connectYourParty.webInterface.service;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@WebService
@Path("/service")
public interface IServiceRoute {

    @POST()
    @Path("addService")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response addService(MultipartBody body);

    @GET()
    @Path("getServicesInterface")
    @Produces(MediaType.APPLICATION_JSON)
    Response getServicesInterface();
}
