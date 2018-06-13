package connectYourParty.service;

import connectYourParty.FileReader;
import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.photo.IPhotoDatabase;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.database.service.ServiceRegistry;
import connectYourParty.modulesLogic.photo.ServiceUser.IPhotoServiceUser;
import connectYourParty.modulesLogic.photo.ServiceUser.PhotoServiceUser;
import connectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import connectYourParty.modulesLogic.photo.interpreter.PhotoInterpreter;
import connectYourParty.requestObjects.photo.PhotoServiceHolder;
import connectYourParty.webInterface.photo.IPhotoModule;
import connectYourParty.webInterface.photo.PhotoModule;
import connectYourParty.webInterface.service.IServiceRoute;
import connectYourParty.webInterface.service.ServiceRoute;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Arquillian.class)
public class ServiceModuleTest {

    @EJB private IServiceRegistry registry;

    @EJB private IPhotoServiceUser module;

    @EJB private IServiceRoute route;

    @EJB private IPhotoModule photoRoute;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addPackage(ServiceRegistry.class.getPackage())
                .addPackage(IServiceRoute.class.getPackage())
                .addPackage(ServiceRoute.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(PhotoServiceUser.class.getPackage())
                .addPackage(IPhotoInterpreter.class.getPackage())
                .addPackage(PhotoInterpreter.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(IPhotoModule.class.getPackage())
                .addPackage(PhotoModule.class.getPackage())
                .addPackage(IPhotoServiceUser.class.getPackage())
                .addPackage(PhotoServiceUser.class.getPackage())
                .addPackage(IPhotoDatabase.class.getPackage())
                .addPackage(IServiceRegistry.class.getPackage())
                .addPackage(ServiceRegistry.class.getPackage())
                .addPackage(IServiceRegistry.class.getPackage());
    }

    @Before
    @After
    public void clean(){
        this.registry.clean();
    }

    @Test
    public void addService() throws Exception{
        int nbService = module.getServiceList().size();

        ServiceHolder holder = new ServiceHolder(Module.PHOTO, FileReader.readfile());
        registry.addServiceHolder(holder);

        assertEquals(nbService + 1 , module.getServiceList().size());
    }

    @Test
    public void addAndGetNewService() throws Exception{
        DataHandler nameHandler = new DataHandler("test","text/plain");
        DataHandler moduleHandler = new DataHandler("photo","text/plain");

        MultivaluedHashMap header = new MultivaluedHashMap<String,String>();


        Attachment name = new Attachment("name",nameHandler,header);
        Attachment module = new Attachment("service", moduleHandler,header);
        Attachment file = new Attachment("file",new ByteArrayInputStream(FileReader.readfile()),null);

        MultipartBody body = new MultipartBody(Arrays.asList(name,
                module,
                file
        ));

        Response getListResponse = this.photoRoute.getPhotoServices();
        assertEquals(200,getListResponse.getStatus());
        int nbService = ((List)getListResponse.getEntity()).size();


        Response addServiceResp = this.route.addService(body);
        assertEquals(200,addServiceResp.getStatus());
        assertEquals(1,this.registry.getServiceHolder().size());

        getListResponse = this.photoRoute.getPhotoServices();
        assertEquals(200,getListResponse.getStatus());
        assertEquals(nbService+1,((List)getListResponse.getEntity()).size());

        List<Integer> ids = new ArrayList<>();

        for(PhotoServiceHolder holder : (List<PhotoServiceHolder>)getListResponse.getEntity()){
            assertFalse(ids.contains(holder.id));
            ids.add(holder.id);
        }

    }






}
