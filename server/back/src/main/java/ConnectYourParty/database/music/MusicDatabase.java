package ConnectYourParty.database.music;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.exception.AddMusicException;
import ConnectYourParty.exception.NoSuchMusicException;
import ConnectYourParty.exception.NoSuchPhotoException;

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
    public void removeMusic(Music music){
        music = entityManager.merge(music);
        entityManager.remove(music);
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
    public void clean() {
        for(Music music : this.getMusicList()){
            this.removeMusic(music);
        }
    }
}
