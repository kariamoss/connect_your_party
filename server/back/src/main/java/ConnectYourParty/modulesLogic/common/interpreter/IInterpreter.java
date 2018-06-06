package ConnectYourParty.modulesLogic.common.interpreter;

import ConnectYourParty.exception.NoSuchServiceException;

import javax.ejb.Local;
import java.net.URL;

@Local
public interface IInterpreter {
    void sendOAuthCode(String code, String serviceName) throws NoSuchServiceException;
    URL retrieveOAuthURL(String serviceName) throws NoSuchServiceException;
}
