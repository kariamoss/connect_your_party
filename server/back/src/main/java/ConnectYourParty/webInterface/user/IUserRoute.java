package ConnectYourParty.webInterface.user;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@WebService
@Path("/user")
public interface IUserRoute {

    @GET
    @Path("/list")
    Response getUserList();
}
