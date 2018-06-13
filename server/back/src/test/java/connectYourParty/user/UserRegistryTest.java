package connectYourParty.user;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.businessObjects.music.Music;
import connectYourParty.database.DbMock;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.database.user.UserRegistry;
import connectYourParty.exception.NoSuchUserException;
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

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserRegistryTest {

    private String code = "AQAvAUWhctqACSyWF6fRoTys0OYwg9p6JXjSdlgO24Ds4X77nwswGb3U0AfTxM0phO_3nw_ykqm_bIHJRtf1EvdvP5YtloANTVy1fGFI-0K20iJ-eqmfYbQijegaquIctpLbmU3CeZGNwRxR71PESo172vEZjG6M_50MCBlWhLSMmBB_56ni-79Da45qXDDuFYoWF2FtMKz5UrqRhH8vInTWpAfUSgyS-bwjwy9MdN93SRi86WQclkcew79lCWh59QCGufPC304-h8hCQwJzqJr7S3CY61lAL0Nh52U-haLO0nj2Mh-mRgqTqsvdZSCebDYCd_PyBKhYDxnXV-qWVEVHGAK0RDwf1JA89p3sJHCI5IYmJj_wld-XRDfQGeFm_opwuDrxHzLWRHFxkvPgbBtljRWcM32ZdjShS0K8I5JOJsN1DAM8dpU8CdJ2fJ4aumXJVxKH";

    @EJB
    private IUserRegistry registry;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(UserRegistry.class.getPackage())
                .addPackage(Music.class.getPackage())
                .addPackage(IUserRegistry.class.getPackage());
    }

    @Before
    public void init(){
        this.registry.clean();
    }

    @Test
    public void listTest(){
        assertEquals(4,registry.getUserList().size());
    }

    @Test
    public void findUserTest() throws Exception{
        assertEquals(DbMock.user.getName(),
                this.registry.getUserById(DbMock.user.getName()).getName());
    }

    @Test
    public void addTokenTest() throws Exception{
        User user = this.registry.getUserById(DbMock.user.getName());
        int nbToken = user.getTokenList().size();

        Token token = new Token("salu","Dropbox","aaaa");
        Token token2 = new Token("azezae","Dropbox","azezae");

        this.registry.addToken(user,token);
        this.registry.addToken(user,token2);

        user = this.registry.getUserById(DbMock.user.getName());
        assertEquals(nbToken+1,user.getTokenList().size());
    }

    @Test(expected = NoSuchUserException.class)
    public void noUserTest() throws Exception{
        this.registry.getUserById("qsqsqsqs");
    }

    @Test
    public void addDifferentServiceTokenTest() throws Exception{
        User user = this.registry.getUserById(DbMock.user.getName());
        assertEquals(0,user.getTokenList().size());

        Token token = new Token("salu","Dropbox","aaaa");
        Token token2 = new Token(code,"Spotify",code);

        this.registry.addToken(user,token);
        this.registry.addToken(user,token2);

        user = this.registry.getUserById(DbMock.user.getName());
        assertEquals(1,user.getTokenList().size());
    }
}
