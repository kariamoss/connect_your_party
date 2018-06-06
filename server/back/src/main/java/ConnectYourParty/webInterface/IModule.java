package ConnectYourParty.webInterface;

import ConnectYourParty.exception.NoSuchServiceException;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@WebService
public interface IModule {

    @POST()
    @Path("sendOAuthCode")
    @Consumes(MediaType.APPLICATION_JSON)
    Response sendOAuthCode(String code, String serviceName) throws NoSuchServiceException;

    @GET()
    @Path("retrieveOAuthURL/{service}")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieveOAuthURL(@PathParam("service") String serviceName) throws NoSuchServiceException;
}
