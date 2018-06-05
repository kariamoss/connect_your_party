package ConnectYourParty.services.photo;


import ConnectYourParty.services.IService;

import javax.json.JsonArray;

public interface IMusicService extends IService {

    JsonArray searchMusic(String search);
}
