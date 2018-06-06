package ConnectYourParty.webInterface.music;

import ConnectYourParty.requestObjects.music.MusicEventHolder;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.json.Json;
import javax.json.JsonArray;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/music")
@WebService
public interface IMusicModule {

    @POST()
    @Path("addMusic")
    @Consumes(MediaType.APPLICATION_JSON)
    Response addMusicToEvent(MusicEventHolder musicEventHolder);

    @GET()
    @Path("searchMusic/{service}/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    Response searchMusic(@PathParam("search") String search, @PathParam("service") String service);

    @GET()
    @Path("getUserToken")
    @Produces(MediaType.APPLICATION_JSON)
    Response getUserToken();

    @GET()
    @Path("getListMusic/{event}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getListMusic(@PathParam("event") String event);


}
