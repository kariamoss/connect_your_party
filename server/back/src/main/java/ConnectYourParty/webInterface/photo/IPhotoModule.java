package ConnectYourParty.webInterface.photo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/photo")
public interface IPhotoModule {

    @POST()
    @Path("addPhoto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPhoto();

    @GET()
    @Path("getPhotos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhotos();

    @GET()
    @Path("getPhotoServices")
    @Produces("application/json")
    public Response getPhotoServices();
}
