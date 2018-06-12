package ConnectYourParty.webInterface.music;


import ConnectYourParty.modulesLogic.music.interpreter.IMusicInterpreter;
import ConnectYourParty.webInterface.CorsAdder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import java.util.logging.Level;

@Stateless
public class MusicModule implements IMusicModule {

    @EJB
    IMusicInterpreter musicInterpreter;

    Logger logger = Logger.getLogger(MusicModule.class.getName());

    @Override
    public Response addMusicToEvent(String idSong, String service) {
        try {
            logger.log(Level.INFO, "Adding music " + idSong);
            musicInterpreter.addMusic(idSong, service);
            return CorsAdder.addCors(Response.ok()).build();
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }

    @Override
    public Response searchMusic(String service, String search) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.searchMusic(search, service)))
                    .build();
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }

    @Override
    public Response listMusic(String service) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.getListMusic(service)))
                    .build();
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }

    @Override
    public Response getInfoMusic(String service, String song) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.getInfoFromMusicId(song, service)))
                    .build();
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }

    @Override
    public Response getPlaylistUrl(String service, String event) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.getPlaylistUrlFromEvent(service, event)))
                    .build();
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }
}
