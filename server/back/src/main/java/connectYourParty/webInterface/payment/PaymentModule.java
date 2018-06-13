package connectYourParty.webInterface.payment;

import connectYourParty.exception.NoSuchUserException;
import connectYourParty.modulesLogic.payment.interpreter.IPaymentInterpreter;
import connectYourParty.webInterface.CorsAdder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Arrays;
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
            URL url = interpreter.pay(target, amount, serviceName, user);
            return CorsAdder.addCors(Response.ok(url.toString())).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            return CorsAdder.corsResponse().status(Response.Status.NOT_ACCEPTABLE).build();
        }

    }

    @Override
    public Response confirm(String payerId, String userId, String serviceName) {
        try{
            this.interpreter.confirm(payerId,userId,serviceName);
            return CorsAdder.corsResponse().build();
        } catch (Exception e){
            logger.log(Level.SEVERE,Arrays.toString(e.getStackTrace()));
            return CorsAdder.corsResponse().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
