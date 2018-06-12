package connectYourParty.database.music;

import connectYourParty.businessObjects.music.Music;
import connectYourParty.businessObjects.music.Playlist;
import connectYourParty.exception.music.AddMusicException;
import connectYourParty.exception.music.AddPlaylistException;
import connectYourParty.exception.music.NoSuchMusicException;
import connectYourParty.exception.music.NoSuchPlaylistException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class MusicDatabase implements IMusicDatabase {
    @PersistenceContext
    private EntityManager entityManager;

    /********** Music *********/

    @Override
    public List<Music> getMusicList() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Music> criteria = builder.createQuery(Music.class);
        Root<Music> root =  criteria.from(Music.class);
        criteria.select(root);
        TypedQuery<Music> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public void addMusic(Music music) throws AddMusicException {
        if(this.getMusicList().contains(music)){
            throw new AddMusicException("Can't add music " + music.getTitle() + " - " + music.getId());
        }
        this.entityManager.persist(music);
        this.entityManager.flush();
    }

    @Override
    public Music getMusicFromId(String id) throws NoSuchMusicException {
        Music music = this.entityManager.find(Music.class,id);
        if(music == null){
            throw new NoSuchMusicException();
        }
        return music;
    }

    @Override
    public void removeMusic(Music music){
        music = entityManager.merge(music);
        entityManager.remove(music);
    }

    /********** Playlist *********/

    @Override
    public void addPlaylist(Playlist playlist) throws AddPlaylistException {
        if(this.getPlaylistList().contains(playlist)){
            throw new AddPlaylistException("Can't add playlist " + playlist.getId());
        }
        this.entityManager.persist(playlist);
        this.entityManager.flush();
    }

    @Override
    public List<Playlist> getPlaylistList() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Playlist> criteria = builder.createQuery(Playlist.class);
        Root<Playlist> root =  criteria.from(Playlist.class);
        criteria.select(root);
        TypedQuery<Playlist> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public Playlist getPlaylistFromId(String id) throws NoSuchPlaylistException {
        Playlist playlist = this.entityManager.find(Playlist.class,id);
        if(playlist == null){
            throw new NoSuchPlaylistException("Can't find playlist with this id : " + id);
        }
        return playlist;
    }

    @Override
    public void removePlaylist(Playlist playlist){
        playlist = entityManager.merge(playlist);
        entityManager.remove(playlist);
    }

    @Override
    public void clean() {
        for(Music music : this.getMusicList()){
            this.removeMusic(music);
        }
        for(Playlist playlist : this.getPlaylistList()){
            this.removePlaylist(playlist);
        }
    }
}
