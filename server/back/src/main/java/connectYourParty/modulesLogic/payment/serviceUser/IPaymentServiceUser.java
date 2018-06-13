package connectYourParty.modulesLogic.payment.serviceUser;


import connectYourParty.businessObjects.Token;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface IPaymentServiceUser {

    void pay(String target, double amount, String serviceName, Optional<Token> token);
}
