package ConnectYourParty.webInterface.music;

import ConnectYourParty.modulesLogic.music.interpreter.IMusicInterpreter;
import ConnectYourParty.requestObjects.music.MusicEventHolder;
import ConnectYourParty.webInterface.CorsAdder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.core.Response;

@Stateless
public class MusicModule implements IMusicModule {

    @EJB
    IMusicInterpreter musicInterpreter;

    @Override
    public Response addMusicToEvent(MusicEventHolder musicEventHolder) {
        try {
            musicInterpreter.addMusicToEvent(musicEventHolder.spotifyTrack, musicEventHolder.eventId);

            return CorsAdder.addCors(Response.ok()).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @Override
    public Response searchMusic(String search) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.searchMusic(search)))
                    .build();
        }
        catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @Override
    public Response getUserToken() {
        return CorsAdder.addCors(
                Response.status(Response.Status.OK)
                        .entity("BQDdv4wrb_rlgb8BO3Y576MmqfGHDNEb3OzDLxzYaQofrPw5K41SvYCQSREG0pHXUKOI7VQ" +
                                "__u_7aPzxiQPoQFAf7bIcWNpv9aG7YEV26enTgguElKz2MzjpoOl2o2iloGt4NquTmVNFP9" +
                                "XgMdFJsKe6AGSxDG3L5bqt-w"))
                .build();
    }

    @Override
    public Response getListMusic(String event) {
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(musicInterpreter.getListMusic(event)))
                .build();
    }
}
