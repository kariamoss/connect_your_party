package connectYourParty.modulesLogic.music.serviceUser;

import connectYourParty.SpotifyService;
import connectYourParty.businessObjects.Token;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.objects.TokenService;
import connectYourParty.objects.music.MusicService;
import connectYourParty.objects.music.PlaylistService;
import connectYourParty.services.music.IMusicService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class MusicServiceUser implements IMusicServiceUser {
    private List<IMusicService> serviceMusicList;

    @PostConstruct
    public void init(){
        serviceMusicList = new ArrayList<>();
        serviceMusicList.add(new SpotifyService());
    }

    @Override
    public List<MusicService> searchMusic(String music, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException {
        if(token.isPresent()) return this.getService(serviceName).searchMusic(
                music,Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(),
                        token.get().getRefreshToken())));
        else return this.getService(serviceName).searchMusic(music,Optional.empty());
    }

    @Override
    public MusicService getInfoFromMusicId(String id, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException {
        if(token.isPresent()) return this.getService(serviceName).getInfoFromId(
                id,Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(),
                        token.get().getRefreshToken())));
        else return this.getService(serviceName).getInfoFromId(id,Optional.empty());
    }

    @Override
    public void addMusicFromId(String id, String playlist, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException {
        if(token.isPresent()) this.getService(serviceName).addMusicFromId(
                id, playlist,Optional.of(new TokenService(token.get().getCode(),
                        token.get().getAccessToken(), token.get().getRefreshToken())));
        else this.getService(serviceName).addMusicFromId(id, playlist,Optional.empty());
    }

    @Override
    public List<MusicService> getMusicFromPlaylist(String playlist, String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, MissingTokenException {
        if(token.isPresent()) return this.getService(serviceName).getMusicFromPlaylist(
                playlist,Optional.of(new TokenService(token.get().getCode(),
                        token.get().getAccessToken(), token.get().getRefreshToken())));
        else return this.getService(serviceName).getMusicFromPlaylist(playlist,Optional.empty());
    }

    @Override
    public PlaylistService addPlaylist(String serviceName,Optional<Token> token) throws NoSuchServiceException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException {
        if(token.isPresent()) return this.getService(serviceName).addPlaylist(
                Optional.of(new TokenService(token.get().getCode(),
                        token.get().getAccessToken(), token.get().getRefreshToken())));
        else return this.getService(serviceName).addPlaylist(Optional.empty());
    }

    private IMusicService getService(String serviceName) throws NoSuchServiceException {
        for(IMusicService service : serviceMusicList){
            if(service.getServiceName().equals(serviceName)){
                return service;
            }
        }
        throw new NoSuchServiceException("Unknown service " + serviceName);
    }
}
