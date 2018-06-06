package ConnectYourParty.service;

import ConnectYourParty.FileReader;
import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.database.photo.IPhotoDatabase;
import ConnectYourParty.database.photo.PhotoDatabase;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.database.service.ServiceRegistry;
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
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class RegistryTest {


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IServiceRegistry.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(ServiceRegistry.class.getPackage());
    }

    @EJB
    private IServiceRegistry db;

    @Before
    public void clean(){
        this.db.clean();
    }

    @Test
    public void addTest() throws Exception{
        byte[] bin = FileReader.readfile();
        ServiceHolder holder = new ServiceHolder(Module.PHOTO,bin);
        ServiceHolder holder2 = new ServiceHolder(Module.PHOTO,bin);
        db.addServiceHolder(holder);
        db.addServiceHolder(holder2);
        assertEquals(db.getServiceHolder().size(),2);
        assertTrue(db.getServiceHolder().contains(holder));
        assertTrue(db.getServiceHolder().contains(holder2));
    }

    @Test
    public void removeTest(){
        ServiceHolder holder = new ServiceHolder(Module.MUSIC,new byte[18]);

        db.addServiceHolder(holder);
        assertEquals(db.getServiceHolder().size(),1);

        db.remove(holder);
        assertEquals(db.getServiceHolder().size(),0);
    }

    @Test
    public void serviceByModuleTest(){
        ServiceHolder musicHolder = new ServiceHolder(Module.MUSIC,new byte[18]);
        ServiceHolder photoHolder = new ServiceHolder(Module.PHOTO,new byte[18]);

        db.addServiceHolder(musicHolder);
        db.addServiceHolder(photoHolder);

        assertEquals(db.getServiceHolderFromModule(Module.MUSIC).size(),1);
        assertTrue(db.getServiceHolderFromModule(Module.MUSIC).contains(musicHolder));

        assertEquals(db.getServiceHolderFromModule(Module.PHOTO).size(),1);
        assertTrue(db.getServiceHolderFromModule(Module.PHOTO).contains(photoHolder));
    }
}
