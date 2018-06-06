package ConnectYourParty.music;

import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.database.music.MusicDatabase;
import ConnectYourParty.exception.AddMusicException;
import ConnectYourParty.exception.NoSuchMusicException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class DbTest {

    @EJB
    IMusicDatabase db;

    @Before
    public void clean(){
        this.db.clean();
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IMusicDatabase.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(MusicDatabase.class.getPackage());
    }

    @Test
    public void addMusicTest() throws Exception{
        Music music = new Music("salut", "spotify", "Pitbull", "Dalle");

        db.addMusic(music);
        List<Music> photos = db.getMusicList();
        assertEquals(photos.get(0),music);
    }

    @Test
    public void emptyListTest(){
        assertEquals(db.getMusicList().size(),0);
    }

    @Test
    public void removeTest() throws Exception{
        Music music = new Music("salut", "spotify", "Pitbull", "Dalle");

        db.addMusic(music);
        List<Music> musics = db.getMusicList();
        assertEquals(musics.get(0),music);

        db.removeMusic(music);

        assertEquals(0,db.getMusicList().size());
    }

    @Test(expected = AddMusicException.class)
    public void doublePhoto() throws AddMusicException{
        Music music1 = new Music("salut", "spotify", "Pitbull", "Dalle");
        Music music2 = new Music("salut", "spotify", "Pitbull", "Dalle");

        db.addMusic(music1);
        db.addMusic(music2);
    }

    @Test
    public void photoRetrievalTest() throws Exception{
        String id = "salut";
        Music music = new Music(id, "spotify", "Pitbull", "Dalle");
        db.addMusic(music);

        assertEquals(music, db.getMusicFromId(id));
    }

    @Test(expected = NoSuchMusicException.class)
    public void photoFailRetrievalTest() throws Exception{
        String id = "salut";
        Music music = new Music(id, "spotify", "Pitbull", "Dalle");
        db.addMusic(music);

        db.getMusicFromId("fff");
    }
}
