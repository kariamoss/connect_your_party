package ConnectYourParty.webInterface.photo;

import ConnectYourParty.requestObjects.Photo;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/photo")
public interface IPhotoModule {

    @POST()
    @Path("addPhoto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPhoto(Photo photo);

    @GET()
    @Path("getPhotos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhotos();
}
