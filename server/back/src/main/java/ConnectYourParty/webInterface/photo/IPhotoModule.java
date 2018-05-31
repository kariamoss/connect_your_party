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
     * Take a multipart form data as input with multiple entries
     * name : name of the photo
     * service : name of the service, value must be equal to one return by getPhotoService
     * e.g "dropbox"
     * photo : file to be uploaded to the service
    */
    @POST()
    @Path("addPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response addPhoto(MultipartBody body);

    /**
     * Return a list of the photo uploaded in our system
     * as a json array.
     * Example:
     *[{
     *   "photoPath": "/event1/photo1.jpg",
     *   "name": "/Flower-icon3.jpg",
     *   "user": "Jehan Milleret"
     * }, {
     *   "photoPath": "/event1/photo2.jpeg",
     *   "name": "/myparty20156jpeg",
     *   "user": "Jehan Milleret"
     * }]
     * @return JsonArray containing information about the photo saved
     */
    @GET()
    @Path("getPhotoList")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPhotoList();

    /**
     * Take a path as input that will retrieve a photo in our system
     * Every path can be asked by using the method {@link IPhotoModule#getPhotoList()}
     * @param event the event (path) were the photo is stored
     * @param name the name of the image saved
     * @return byte[] containing the image requested
     */
    @GET()
    @Path("getPhoto/{event}/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response getPhoto(String event, String name);

    /**
     * Return the services as a json array containing
     * the information about all the services from the photo module
     * Example:
     *[{
     *   "name": "dropbox",
     *   "icon": "www.iconfinder.com/icons/173882"
     * }, {
     *   "name": "Album photo de Connect Your Party",
     *   "icon": "icon.png"
     * }]
     * @return JsonArray containing information about the photo services
     */
    @GET()
    @Path("getPhotoServices")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPhotoServices();
}
