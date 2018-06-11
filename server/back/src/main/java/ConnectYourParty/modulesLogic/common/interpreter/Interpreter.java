package ConnectYourParty.modulesLogic.common.interpreter;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.database.user.IUserRegistry;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exception.NoSuchUserException;
import ConnectYourParty.modulesLogic.common.serviceUser.IServiceUser;
import ConnectYourParty.requestObjects.request.OAuthHolder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.net.URL;

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
