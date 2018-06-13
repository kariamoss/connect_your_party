package connectYourParty.modulesLogic.common.serviceUser;

import connectYourParty.businessObjects.Token;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.requestObjects.request.OAuthHolder;

import javax.ejb.Local;

@Local
public interface IServiceUser {
    Token getToken(String code, String serviceName) throws NoSuchServiceException;

    OAuthHolder getOAuthURL(String serviceName) throws NoSuchServiceException;
}
