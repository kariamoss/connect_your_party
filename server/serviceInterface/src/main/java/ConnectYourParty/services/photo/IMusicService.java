package ConnectYourParty.services.photo;


import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.services.IService;

import java.util.List;

public interface IMusicService extends IService {

    List<MusicService> searchMusic(String search);
}
