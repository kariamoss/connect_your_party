package connectYourParty.modulesLogic.payment.interpreter;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.modulesLogic.payment.serviceUser.IPaymentServiceUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class PaymentInterpreter implements IPaymentInterpreter {

    @EJB
    private IPaymentServiceUser paymentServiceUser;

    @EJB
    private IUserRegistry userRegistry;

    @Override
    public void pay(String target, double amount, String serviceName, String userId) throws NoSuchUserException {
        Optional<Token> token;

        User user = userRegistry.getUserById(userId);

        if (user.getToken(serviceName).isPresent())
            token = Optional.of(user.getToken(serviceName).get());
        else token = Optional.empty();

        paymentServiceUser.pay(target, amount, serviceName, token);
    }
}