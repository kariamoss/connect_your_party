package connectYourParty.modulesLogic.common.interpreter;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.modulesLogic.common.serviceUser.IServiceUser;
import connectYourParty.requestObjects.request.OAuthHolder;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class Interpreter implements IInterpreter {

    @EJB
    private IUserRegistry userRegistry;

    @EJB
    private IServiceUser services;

    @Override
    public void sendOAuthCode(String code, String serviceName, String userId) throws NoSuchServiceException, NoSuchUserException {
        Token token = services.getToken(code, serviceName);

        User user = userRegistry.getUserById(userId);

        userRegistry.addToken(user,token);
    }

    @Override
    public OAuthHolder retrieveOAuthURL(String serviceName) throws NoSuchServiceException {
        return services.getOAuthURL(serviceName);
    }
}
