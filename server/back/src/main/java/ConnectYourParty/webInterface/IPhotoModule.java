package ConnectYourParty.webInterface;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/photo")
public interface IPhotoModule {

    @GET()
    @Path("add")
    public String addPhoto();
}
