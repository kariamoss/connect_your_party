package ConnectYourParty.modulesLogic.common.serviceUser;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.exception.NoSuchServiceException;

import javax.ejb.Local;
import java.net.URL;

@Local
public interface IServiceUser {
    Token getToken(String code, String serviceName) throws NoSuchServiceException;

    URL getOAuthURL(String serviceName) throws NoSuchServiceException;
}
