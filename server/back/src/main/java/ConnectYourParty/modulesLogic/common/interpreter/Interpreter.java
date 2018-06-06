package ConnectYourParty.modulesLogic.common.interpreter;

import ConnectYourParty.database.token.ITokenDatabase;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.modulesLogic.common.serviceUser.IServiceUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.net.URL;

@Stateless
public class Interpreter implements IInterpreter {

    @EJB
    private ITokenDatabase tokenDatabase;

    @EJB
    private IServiceUser services;

    @Override
    public void sendOAuthCode(String code, String serviceName) throws NoSuchServiceException {
        tokenDatabase.addToken(services.getToken(code, serviceName));
    }

    @Override
    public URL retrieveOAuthURL(String serviceName) throws NoSuchServiceException {
        return services.getOAuthURL(serviceName);
    }
}
