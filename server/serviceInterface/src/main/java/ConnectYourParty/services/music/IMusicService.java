package ConnectYourParty.services.music;


import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.services.IService;

import java.util.List;

public interface IMusicService extends IService {

    List<MusicService> searchMusic(String search);

    MusicService getInfoFromId(String id);
}
