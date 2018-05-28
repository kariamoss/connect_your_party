package ConnectYourParty.webInterface.photo;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/photo")
public interface IPhotoModule {

    @POST()
    @Path("addPhoto")
    public String addPhoto();

    @GET()
    @Path("getPhotos")
    public String getPhotos();

    @GET()
    @Path("getPhotoServices")
    @Produces("application/json")
    public Response getPhotoServices();
}
