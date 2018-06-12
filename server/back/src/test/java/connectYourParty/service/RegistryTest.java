package connectYourParty.service;

import connectYourParty.FileReader;
import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.database.service.ServiceRegistry;
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
    public void removeTest() throws Exception{
        ServiceHolder holder = new ServiceHolder(Module.MUSIC,FileReader.readfile());

        db.addServiceHolder(holder);
        assertEquals(db.getServiceHolder().size(),1);

        db.remove(holder);
        assertEquals(db.getServiceHolder().size(),0);
    }

    @Test
    public void serviceByModuleTest() throws Exception{
        ServiceHolder musicHolder = new ServiceHolder(Module.MUSIC,FileReader.readfile());
        ServiceHolder photoHolder = new ServiceHolder(Module.PHOTO,FileReader.readfile());

        db.addServiceHolder(musicHolder);
        db.addServiceHolder(photoHolder);

        assertEquals(db.getServiceHolderFromModule(Module.MUSIC).size(),1);
        assertTrue(db.getServiceHolderFromModule(Module.MUSIC).contains(musicHolder));

        assertEquals(db.getServiceHolderFromModule(Module.PHOTO).size(),1);
        assertTrue(db.getServiceHolderFromModule(Module.PHOTO).contains(photoHolder));
    }
}
