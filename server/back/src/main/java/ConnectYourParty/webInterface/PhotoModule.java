package ConnectYourParty.webInterface;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/photo")
public class PhotoModule {

    @GET()
    @Path("add")
    public String addPhoto(){
        return "hello";
    }
}
