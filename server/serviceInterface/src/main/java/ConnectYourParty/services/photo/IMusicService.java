package ConnectYourParty.services.photo;


import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.services.IService;

public interface IMusicService extends IService {

    MusicService searchMusic(String search);
}
