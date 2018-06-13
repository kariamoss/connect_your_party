package connectYourParty.modulesLogic.payment.interpreter;

import connectYourParty.exception.NoSuchUserException;

public interface IPaymentInterpreter {
    void pay(String target, double amount, String serviceName, String userId) throws NoSuchUserException, NoSuchUserException;
}
