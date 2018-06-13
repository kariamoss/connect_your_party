package connectYourParty.payment;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.DbMock;
import connectYourParty.database.music.IMusicDatabase;
import connectYourParty.database.music.MusicDatabase;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.database.service.ServiceRegistry;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.database.user.UserRegistry;
import connectYourParty.modulesLogic.payment.interpreter.IPaymentInterpreter;
import connectYourParty.modulesLogic.payment.interpreter.PaymentInterpreter;
import connectYourParty.modulesLogic.payment.serviceUser.IPaymentServiceUser;
import connectYourParty.modulesLogic.payment.serviceUser.PaymentServiceUser;
import connectYourParty.objects.TokenService;
import connectYourParty.services.payment.IPaymentService;
import connectYourParty.webInterface.music.IMusicModule;
import connectYourParty.webInterface.music.MusicModule;
import connectYourParty.webInterface.payment.IPaymentModule;
import connectYourParty.webInterface.payment.PaymentModule;
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
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class PaymentModuleTest {

    private String serviceName = "Paypal";

    @EJB private IPaymentModule module;

    @EJB private IServiceRegistry serviceRegistry;

    @EJB private IUserRegistry userRegistry;

    private IPaymentService service;

    @Before
    public void init(){
        this.service = new TestPaymentService();
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(DbMock.class.getPackage())
                .addPackage(IServiceRegistry.class.getPackage())
                .addPackage(ServiceRegistry.class.getPackage())
                .addPackage(IPaymentModule.class.getPackage())
                .addPackage(PaymentModule.class.getPackage())
                .addPackage(IPaymentInterpreter.class.getPackage())
                .addPackage(PaymentInterpreter.class.getPackage())
                .addPackage(IUserRegistry.class.getPackage())
                .addPackage(UserRegistry.class.getPackage())
                .addPackage(IPaymentServiceUser.class.getPackage())
                .addPackage(PaymentServiceUser.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @Test
    public void payTest() throws Exception{
        this.serviceRegistry.addServiceHolder(new ServiceHolder(Module.PAYMENT,
                this.service.getClass()));

        User user = this.userRegistry.getUserById(DbMock.user.getName());

        this.userRegistry.addToken(user,new Token("code","Paypal","code"));

        Response url =  this.module.pay("salut",100.00,"e",DbMock.user.getName(),serviceName);

        assertEquals(200,url.getStatus());

        assertNotNull(url.getEntity());
        assertEquals(TestPaymentService.urlToReturn,url.getEntity());


    }
}
