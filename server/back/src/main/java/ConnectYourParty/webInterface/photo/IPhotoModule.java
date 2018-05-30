package ConnectYourParty.webInterface.photo;

import ConnectYourParty.requestObjects.photo.UploadRequest;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import java.io.InputStream;

@Path("/photo")
public interface IPhotoModule {


    /**
     * take a multipart form data as input with multiple entries
     * name : name of the photo
     * service : name of the service, value must be equal to one return by getPhotoService
     * e.g "dropbox"
     * photo : file to be uploaded to the service
    */
    @POST()
    @Path("addPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response addPhoto(MultipartBody body);

    @GET()
    @Path("getPhotoList")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPhotoList();

    @GET()
    @Path("getPhoto/{path}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response getPhoto(String path);

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
