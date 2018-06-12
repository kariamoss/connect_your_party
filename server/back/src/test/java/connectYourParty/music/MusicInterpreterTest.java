package connectYourParty.music;

import connectYourParty.database.DbMock;
import connectYourParty.database.music.IMusicDatabase;
import connectYourParty.database.music.MusicDatabase;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.exception.music.AddPlaylistException;
import connectYourParty.exception.music.NoSuchPlaylistException;
import connectYourParty.exceptions.MissingTokenException;
import connectYourParty.exceptions.music.CannotCreatePlaylistException;
import connectYourParty.exceptions.music.GetMusicErrorException;
import connectYourParty.modulesLogic.music.interpreter.IMusicInterpreter;
import connectYourParty.modulesLogic.music.interpreter.MusicInterpreter;
import connectYourParty.modulesLogic.music.serviceUser.IMusicServiceUser;
import connectYourParty.modulesLogic.music.serviceUser.MusicServiceUser;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@Ignore
@RunWith(Arquillian.class)
public class MusicInterpreterTest {

    @EJB
    private IMusicDatabase db;

    @EJB
    private IMusicInterpreter musicInterpreter;


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
                .addPackage(IMusicInterpreter.class.getPackage())
                .addPackage(IMusicServiceUser.class.getPackage())
                .addPackage(MusicServiceUser.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(MusicInterpreter.class.getPackage());
    }

    @Ignore
    public void createPlaylistWhenEmpty() throws NoSuchServiceException, NoSuchPlaylistException, AddPlaylistException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException, NoSuchUserException {

        assertEquals(0, db.getPlaylistList().size());

        musicInterpreter.addMusic("idMusic", "Spotify");

        assertEquals(1, db.getPlaylistList().size());
    }
}
