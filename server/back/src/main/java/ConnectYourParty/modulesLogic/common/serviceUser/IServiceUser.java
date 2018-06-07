package ConnectYourParty.modulesLogic.common.serviceUser;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.requestObjects.request.OAuthHolder;

import javax.ejb.Local;
import java.net.URL;

@Local
public interface IServiceUser {
    Token getToken(String code, String serviceName) throws NoSuchServiceException;

    OAuthHolder getOAuthURL(String serviceName) throws NoSuchServiceException;
}
