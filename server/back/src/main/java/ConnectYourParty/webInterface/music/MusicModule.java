package ConnectYourParty.webInterface.music;

import ConnectYourParty.exception.AddMusicException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.modulesLogic.music.interpreter.IMusicInterpreter;
import ConnectYourParty.requestObjects.music.MusicEventHolder;
import ConnectYourParty.webInterface.CorsAdder;
import ConnectYourParty.webInterface.photo.PhotoModule;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import java.util.logging.Level;

@Stateless
public class MusicModule implements IMusicModule {

    @EJB
    IMusicInterpreter musicInterpreter;

    Logger logger = Logger.getLogger(MusicModule.class.getName());

    @Override
    public Response addMusicToEvent(MusicEventHolder musicEventHolder) {
        try {
            musicInterpreter.addMusicToEvent(musicEventHolder.song, musicEventHolder.eventId, musicEventHolder.service);

            return CorsAdder.addCors(Response.ok()).build();
        }
        catch (NoSuchServiceException | AddMusicException e){
            logger.log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @Override
    public Response searchMusic(String search, String service) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.searchMusic(search, service)))
                    .build();
        }
        catch (NoSuchServiceException e){
            logger.log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @Override
    public Response getUserToken() {
        //TODO Gérer la connexion
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
