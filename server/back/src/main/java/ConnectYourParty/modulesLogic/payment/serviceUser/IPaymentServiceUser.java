package ConnectYourParty.modulesLogic.payment.serviceUser;

import ConnectYourParty.businessObjects.Token;

import java.util.Optional;

public interface IPaymentServiceUser {

    void pay(String target, double amount, String serviceName, Optional<Token> token);
}
