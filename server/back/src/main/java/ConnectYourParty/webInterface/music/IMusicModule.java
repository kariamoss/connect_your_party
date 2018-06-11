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
     * The list of musics added can be found with {@link MusicModule#listMusic(String service)}
     * @param @String idSong and @String service in a FORM_URLENCODED
     */
    @POST()
    @Path("addMusic")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response addMusicToEvent(@FormParam("idSong") String code, @FormParam("service") String service);

    /**
     * Search for a music in the service passed as argument
     * @param search The research that can contain an artist or a track
     * @param service The service you want to use
     * @return {@link JsonArray} containing a list of {@link ConnectYourParty.requestObjects.music.MusicSearchHolder}
     */
    @GET()
    @Path("searchMusic/{service}/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    Response searchMusic(@PathParam("service") String service, @PathParam("search") String search);

    /**
     * Retrieve the musics available in the event playlist
     * @param service The service you want to use
     * @return {@link JsonArray} containing a list of {@link ConnectYourParty.requestObjects.music.MusicSearchHolder}
     */
    @GET()
    @Path("listMusic/{service}")
    @Produces(MediaType.APPLICATION_JSON)
    Response listMusic(@PathParam("service") String service);

    /**
     * Retrieve basic information about a specific song id
     * @param service The service you want to use
     * @param song The song id
     * @return {@link JsonArray} containing a {@link ConnectYourParty.requestObjects.music.MusicSearchHolder}
     */
    @GET()
    @Path("infoMusic/{service}/{song}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getInfoMusic(@PathParam("service") String service, @PathParam("song") String song);



}
