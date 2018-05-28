package ConnectYourParty.webInterface.photo;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/photo")
public interface IPhotoModule {

    @POST()
    @Path("addPhoto")
    public String addPhoto();

    @GET()
    @Path("getPhotos")
    public String getPhotos();
}
