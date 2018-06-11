package ConnectYourParty.music;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.database.music.MusicDatabase;
import ConnectYourParty.requestObjects.music.MusicEventHolder;
import ConnectYourParty.webInterface.music.IMusicModule;
import ConnectYourParty.webInterface.music.MusicModule;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

@Ignore
@RunWith(Arquillian.class)
public class MusicModuleTest {
    @EJB
    private IMusicDatabase db;

    @EJB
    private IMusicModule musicModule;

    @Before
    public void clean(){
        this.db.clean();
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(DbMock.class.getPackage())
                .addPackage(IMusicDatabase.class.getPackage())
                .addPackage(MusicDatabase.class.getPackage())
                .addPackage(IMusicModule.class.getPackage())
                .addPackage(MusicModule.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @Test
    public void addMusicWithNewPlaylistTest(){
        MusicEventHolder musicEventHolder = new MusicEventHolder();
        musicEventHolder.idSong = "6xqAP7kpdgCy8lERQHh29c";
        musicEventHolder.service = "Spotify";
        //musicModule.addMusicToEvent(musicEventHolder);
    }

    @Test
    public void addMusicWithSamePlaylistTest(){
        MusicEventHolder musicEventHolder = new MusicEventHolder();
        musicEventHolder.idSong = "6xqAP7kpdgCy8lERQHh29c";
        musicEventHolder.service = "Spotify";
        //musicModule.addMusicToEvent(musicEventHolder);
        //musicModule.addMusicToEvent(musicEventHolder);
        //musicModule.addMusicToEvent(musicEventHolder);
    }

    @Test
    public void retrieveMusicTest(){
        MusicEventHolder musicEventHolder = new MusicEventHolder();
        musicEventHolder.idSong = "6xqAP7kpdgCy8lERQHh29c";
        musicEventHolder.service = "Spotify";
        //musicModule.addMusicToEvent(musicEventHolder);
        musicModule.listMusic("Spotify");
    }
}
