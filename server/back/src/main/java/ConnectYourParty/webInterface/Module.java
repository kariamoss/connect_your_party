package ConnectYourParty.webInterface;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.modulesLogic.common.interpreter.IInterpreter;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

public class Module implements IModule {

    @EJB
    private IInterpreter interpreter;

    @Override
    public Response sendOAuthCode(String code, String serviceName) throws NoSuchServiceException {
        interpreter.sendOAuthCode(code, serviceName);

        return CorsAdder.addCors(Response.ok()).build();
    }

    @Override
    public Response retrieveOAuthURL(String serviceName) throws NoSuchServiceException {
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(interpreter.retrieveOAuthURL(serviceName)))
                .build();
    }
}
