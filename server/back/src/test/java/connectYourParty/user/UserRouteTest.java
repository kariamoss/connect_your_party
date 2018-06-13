package connectYourParty.user;


import connectYourParty.businessObjects.music.Music;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.database.user.UserRegistry;
import connectYourParty.requestObjects.UserHolder;
import connectYourParty.webInterface.user.IUserRoute;
import connectYourParty.webInterface.user.UserRoute;
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
