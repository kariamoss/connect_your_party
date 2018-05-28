package ConnectYourParty.webInterface.photo;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/photo")
public interface IPhotoModule {

    @POST()
    @Path("addPhoto")
    public Response addPhoto();

    @GET()
    @Path("getPhotos")
    public Response getPhotos();
}
