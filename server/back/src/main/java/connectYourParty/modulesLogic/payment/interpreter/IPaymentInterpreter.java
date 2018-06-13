package connectYourParty.modulesLogic.payment.interpreter;

import connectYourParty.exception.NoSuchUserException;

import java.net.URL;

public interface IPaymentInterpreter {
    URL pay(String target, double amount, String serviceName, String userId) throws NoSuchUserException, NoSuchUserException;
}
