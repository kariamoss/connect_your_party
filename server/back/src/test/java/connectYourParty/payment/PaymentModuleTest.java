package connectYourParty.payment;

import connectYourParty.database.DbMock;
import connectYourParty.database.music.IMusicDatabase;
import connectYourParty.database.music.MusicDatabase;
import connectYourParty.webInterface.music.IMusicModule;
import connectYourParty.webInterface.music.MusicModule;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
public class PaymentModuleTest {
    

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
    public void payTest(){

    }
}
