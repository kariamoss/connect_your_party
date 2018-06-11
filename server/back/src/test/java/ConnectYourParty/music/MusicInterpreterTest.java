package ConnectYourParty.music;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.music.IMusicDatabase;
import ConnectYourParty.database.music.MusicDatabase;
import ConnectYourParty.database.token.ITokenDatabase;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.music.AddPlaylistException;
import ConnectYourParty.exception.music.NoSuchPlaylistException;
import ConnectYourParty.exceptions.MissingTokenException;
import ConnectYourParty.exceptions.music.CannotCreatePlaylistException;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.modulesLogic.music.interpreter.IMusicInterpreter;
import ConnectYourParty.modulesLogic.music.interpreter.MusicInterpreter;
import ConnectYourParty.modulesLogic.music.serviceUser.IMusicServiceUser;
import ConnectYourParty.modulesLogic.music.serviceUser.MusicServiceUser;
import ConnectYourParty.objects.music.PlaylistService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
@RunWith(Arquillian.class)
public class MusicInterpreterTest {

    @EJB
    private IMusicDatabase db;

    @EJB
    private IMusicInterpreter musicInterpreter;

    @EJB
    ITokenDatabase tokenDB;

    @Before
    public void clean(){
        this.db.clean();
        this.tokenDB.clean();
        tokenDB.addToken(new Token());
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
    public void createPlaylistWhenEmpty() throws NoSuchServiceException, NoSuchPlaylistException, AddPlaylistException, GetMusicErrorException, CannotCreatePlaylistException, MissingTokenException {

        assertEquals(0, db.getPlaylistList().size());

        musicInterpreter.addMusic("idMusic", "Spotify");

        assertEquals(1, db.getPlaylistList().size());
    }
}
