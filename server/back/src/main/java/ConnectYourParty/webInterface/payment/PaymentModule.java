package ConnectYourParty.webInterface.payment;

import ConnectYourParty.exception.NoSuchUserException;
import ConnectYourParty.modulesLogic.payment.interpreter.IPaymentInterpreter;
import ConnectYourParty.webInterface.CorsAdder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PaymentModule implements IPaymentModule {

    Logger logger = Logger.getLogger(PaymentModule.class.getName());

    @EJB
    private IPaymentInterpreter interpreter;

    @Override
    public Response pay(String target, Double amount, String currency, String user, String serviceName) {

        try {
            interpreter.pay(target, amount, serviceName, user);
            return CorsAdder.addCors(Response.ok()).build();
        } catch (NoSuchUserException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.corsResponse().status(Response.Status.NOT_ACCEPTABLE).build();
        }

    }
}
