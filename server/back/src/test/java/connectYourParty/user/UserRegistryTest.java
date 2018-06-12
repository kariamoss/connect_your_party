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
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserRegistryTest {

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
        assertEquals(0,user.getTokenList().size());

        Token token = new Token("salu","Dropbox","aaaa");
        Token token2 = new Token("azezae","Dropbox","azezae");

        this.registry.addToken(user,token);
        this.registry.addToken(user,token2);

        user = this.registry.getUserById(DbMock.user.getName());
        assertEquals(1,user.getTokenList().size());
    }

    @Test(expected = NoSuchUserException.class)
    public void noUserTest() throws Exception{
        this.registry.getUserById("qsqsqsqs");
    }
}
