package ConnectYourParty.webInterface.music;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.music.AddPlaylistException;
import ConnectYourParty.exception.music.NoSuchPlaylistException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.modulesLogic.music.interpreter.IMusicInterpreter;
import ConnectYourParty.requestObjects.music.MusicEventHolder;
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
    public Response addMusicToEvent(MusicEventHolder musicEventHolder) {
        try {
            musicInterpreter.addMusic(musicEventHolder.idSong, musicEventHolder.service);

            return CorsAdder.addCors(Response.ok()).build();
        }
        catch (NoSuchServiceException | AddPlaylistException | NoSuchPlaylistException | GetMusicErrorException e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        } catch (Exception e){
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }

    @Override
    public Response searchMusic(String search, String service) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.searchMusic(search, service)))
                    .build();
        }
        catch (NoSuchServiceException | GetMusicErrorException e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
        catch (Exception e){
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }

    @Override
    public Response listMusic(String service) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.getListMusic(service)))
                    .build();
        }
        catch (NoSuchServiceException | NoSuchPlaylistException e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
        catch (Exception e){
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }

    @Override
    public Response getInfoMusic(String service, String song) {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(musicInterpreter.getInfoFromMusicId(song, service)))
                    .build();
        }
        catch (NoSuchServiceException | GetMusicErrorException e){
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
        catch (Exception e){
            return CorsAdder.addCors(Response.status(Response.Status.NOT_ACCEPTABLE)).build();
        }
    }
}
