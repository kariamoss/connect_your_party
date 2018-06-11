package ConnectYourParty.user;


import ConnectYourParty.businessObjects.User;
import ConnectYourParty.businessObjects.music.Music;
import ConnectYourParty.database.user.IUserRegistry;
import ConnectYourParty.database.user.UserRegistry;
import ConnectYourParty.requestObjects.UserHolder;
import ConnectYourParty.webInterface.user.IUserRoute;
import ConnectYourParty.webInterface.user.UserRoute;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserRouteTest {

    @EJB
    private IUserRoute route;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(UserRegistry.class.getPackage())
                .addPackage(IUserRoute.class.getPackage())
                .addPackage(UserRoute.class.getPackage())
                .addPackage(Music.class.getPackage())
                .addPackage(IUserRegistry.class.getPackage());
    }

    @Test
    public void UserListTest(){
        Response listResponse = route.getUserList();

        UserHolder[] users = (UserHolder[]) listResponse.getEntity();

        assertEquals(4,users.length);
    }

}
