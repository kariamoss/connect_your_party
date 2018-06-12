package connectYourParty.modulesLogic.common.interpreter;

import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.requestObjects.request.OAuthHolder;

import javax.ejb.Local;

@Local
public interface IInterpreter {
    void sendOAuthCode(String code, String serviceName, String userId) throws NoSuchServiceException, NoSuchUserException;
    OAuthHolder retrieveOAuthURL(String serviceName) throws NoSuchServiceException;
}
