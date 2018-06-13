package connectYourParty.modulesLogic.payment.interpreter;

import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.requestObjects.photo.PhotoServiceHolder;

import java.net.URL;
import java.util.List;

public interface IPaymentInterpreter {
    URL pay(String target, double amount, String serviceName, String userId) throws NoSuchUserException, NoSuchUserException;

    void confirm(String payerId, String userId, String serviceName) throws NoSuchUserException,NoSuchServiceException;

    List<PhotoServiceHolder> getServices();
}
