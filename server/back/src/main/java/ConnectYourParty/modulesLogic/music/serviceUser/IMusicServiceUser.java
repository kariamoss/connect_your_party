package ConnectYourParty.modulesLogic.music.serviceUser;

import ConnectYourParty.exception.NoSuchServiceException;

import javax.ejb.Local;
import javax.json.JsonArray;

@Local
public interface IMusicServiceUser {
    JsonArray searchMusic(String music, String serviceName) throws NoSuchServiceException;

}
