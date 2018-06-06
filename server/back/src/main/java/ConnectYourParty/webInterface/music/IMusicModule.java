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

    /**
     * Add a music to the event
     * Take a {@link Json} as input that copy
     * the form of a {@link MusicEventHolder}
     * The list of musics added can be found with {@link MusicModule#getListMusic(String event)}
     * @param musicEventHolder Json object containing a {@link MusicEventHolder}
     */
    @POST()
    @Path("addMusic")
    @Consumes(MediaType.APPLICATION_JSON)
    Response addMusicToEvent(MusicEventHolder musicEventHolder);

    /**
     * Search for a music in the service passed as argument
     * @param search The research that can contain an artist or a track
     * @param service The service you want to use
     * @return {@link JsonArray} containing a list of {@link ConnectYourParty.requestObjects.music.MusicSearchHolder}
     */
    @GET()
    @Path("searchMusic/{service}/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    Response searchMusic(@PathParam("search") String search, @PathParam("service") String service);

    /**
     * Get the user token from the service wanted
     * @return Json containg the user token
     */
    @GET()
    @Path("getUserToken")
    @Produces(MediaType.APPLICATION_JSON)
    Response getUserToken();

    /**
     * Retrieve the list of musics of a particular event
     * @param event the list of musics as {@link ConnectYourParty.requestObjects.music.MusicSearchHolder}
     * @return
     */
    @GET()
    @Path("getListMusic/{event}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getListMusic(@PathParam("event") String event);


}
