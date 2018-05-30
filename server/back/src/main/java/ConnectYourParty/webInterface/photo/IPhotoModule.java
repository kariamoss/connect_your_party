package ConnectYourParty.webInterface.photo;

import ConnectYourParty.requestObjects.photo.UploadRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import java.io.InputStream;

@Path("/photo")
public interface IPhotoModule {


    /**
     * take a json input containing 4 entries
     * name : name of the photo (string)
     * format : format of the photo (string)
     * serviceName : service to be used (string)
     * photo : base64 encoding of the photo (string)
     * example :
     * {
     *     name : "test",
     *     format : "jpg",
     *     serviceName : "drive",
     *     photo : "SGkh"
     * }
     *
     * @param photo
     * @return success or failure image
     */
    @POST()
    @Path("addPhoto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response addPhoto(InputStream input, String name);

    @GET()
    @Path("getPhotos")
    Response getPhotos();

    /**
     * Return the services as a json object containing
     * the information about all the services from the photo module
     * example :
     *[{
     *   "name": "dropbox",
     *   "icon": "www.iconfinder.com/icons/173882"
     * }, {
     *   "name": "Album photo de Connect Your Party",
     *   "icon": "icon.png"
     * }]
     * @return JsonObject containing information about the photo services
     */
    @GET()
    @Path("getPhotoServices")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPhotoServices();
}
