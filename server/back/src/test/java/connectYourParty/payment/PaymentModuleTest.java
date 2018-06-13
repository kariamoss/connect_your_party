package connectYourParty.payment;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
public class PaymentModuleTest {

    @EJB private IPaymentModule module;

    @EJB private IServiceRegistry registry;

    private IPaymentService service;

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
    public void payTest(){

    }
}
