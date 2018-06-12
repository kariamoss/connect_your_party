package ConnectYourParty.modulesLogic.payment.interpreter;

import ConnectYourParty.exception.NoSuchUserException;

public interface IPaymentInterpreter {
    void pay(String target, double amount, String serviceName, String userId) throws NoSuchUserException;
}
