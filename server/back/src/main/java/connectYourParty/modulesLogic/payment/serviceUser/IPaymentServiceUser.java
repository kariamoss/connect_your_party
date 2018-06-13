package connectYourParty.modulesLogic.payment.serviceUser;


import connectYourParty.businessObjects.Token;
import connectYourParty.exception.NoSuchServiceException;

import javax.ejb.Local;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Local
public interface IPaymentServiceUser {

    List<URL> pay(String target, double amount, String serviceName, Optional<Token> token);
    void confirm(String payerId,Optional<Token> tok,String serviceName) throws NoSuchServiceException;
}
