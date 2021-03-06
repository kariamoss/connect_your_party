package connectYourParty.webInterface;

import connectYourParty.exception.NoSuchServiceException;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@WebService
public interface IModule {

    @POST()
    @Path("sendOAuthCode")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response sendOAuthCode(@FormParam("code") String code,
                           @FormParam("serviceName") String serviceName,
                           @FormParam("userId") String userId) throws NoSuchServiceException;

    @GET()
    @Path("retrieveOAuthURL/{service}")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieveOAuthURL(@PathParam("service") String serviceName) throws NoSuchServiceException;
}
