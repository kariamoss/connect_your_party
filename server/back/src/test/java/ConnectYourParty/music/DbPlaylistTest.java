package ConnectYourParty.music;

import ConnectYourParty.businessObjects.music.Playlist;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.exception.music.AddPlaylistException;
import ConnectYourParty.exception.music.NoSuchPlaylistException;
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
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class DbPlaylistTest {
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
                .addPackage(IMusicDatabase.class.getPackage());
    }

    @Test
    public void addPlaylistTest() throws Exception{
        Playlist playlist = new Playlist("salut", "Spotify");

        db.addPlaylist(playlist);
        List<Playlist> playlists = db.getPlaylistList();
        assertEquals(playlists.get(0), playlist);
    }

    @Test
    public void emptyListTest(){
        assertEquals(db.getPlaylistList().size(),0);
    }

    @Test
    public void removeTest() throws Exception{
        Playlist playlist = new Playlist("salut", "Spotify");

        db.addPlaylist(playlist);
        List<Playlist> playlists = db.getPlaylistList();
        assertEquals(playlists.get(0),playlist);

        db.removePlaylist(playlist);

        assertEquals(0,db.getPlaylistList().size());
    }

    @Test(expected = AddPlaylistException.class)
    public void doublePlaylist() throws AddPlaylistException{
        Playlist playlist1 = new Playlist("salut", "Spotify");
        Playlist playlist2 = new Playlist("salut", "Spotify");

        db.addPlaylist(playlist1);
        db.addPlaylist(playlist2);
    }

    @Test
    public void playlistRetrievalTest() throws NoSuchPlaylistException, AddPlaylistException {
        String id = "salut";
        Playlist playlist = new Playlist(id, "Spotify");
        db.addPlaylist(playlist);

        assertEquals(playlist, db.getPlaylistFromId(id));
    }

    @Test(expected = NoSuchPlaylistException.class)
    public void playlistFailRetrievalTest() throws Exception {
        String id = "salut";
        Playlist playlist = new Playlist(id, "Spotify");
        db.addPlaylist(playlist);

        db.getPlaylistFromId("fff");
    }
}
