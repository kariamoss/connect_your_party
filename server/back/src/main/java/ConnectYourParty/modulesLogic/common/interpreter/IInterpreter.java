package ConnectYourParty.modulesLogic.common.interpreter;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.NoSuchUserException;
import ConnectYourParty.requestObjects.request.OAuthHolder;

import javax.ejb.Local;
import java.net.URL;

@Local
public interface IInterpreter {
    void sendOAuthCode(String code, String serviceName, String userId) throws NoSuchServiceException, NoSuchUserException;
    OAuthHolder retrieveOAuthURL(String serviceName) throws NoSuchServiceException;
}
