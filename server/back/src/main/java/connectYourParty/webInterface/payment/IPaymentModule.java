package connectYourParty.webInterface.payment;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payment")
@WebService
public interface IPaymentModule {

    @POST
    @Path("pay")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response pay(@FormParam("target") String target,
                 @FormParam("amount") double amount,
                 @FormParam("currency") String currency,
                 @FormParam("userId") String userId,
                 @FormParam("service") String serviceName);

    @POST
    @Path("confirm")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    Response confirm(@FormParam("payer_id") String payerId,
                     @FormParam("userId") String userId,
                     @FormParam("service") String serviceName);

    @GET()
    @Path("getServices")
    @Produces(MediaType.APPLICATION_JSON)
    Response getServices();
}
