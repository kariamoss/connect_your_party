package ConnectYourParty.webInterface;

import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.modulesLogic.common.interpreter.IInterpreter;
import ConnectYourParty.requestObjects.request.NullResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class Module implements IModule {

    Logger logger = Logger.getLogger(Module.class.getName());

    @EJB
    private IInterpreter interpreter;

    @Override
    public Response sendOAuthCode(String code, String serviceName) throws NoSuchServiceException {
        // JSONObject object = new JSONObject(text);

        logger.log(Level.INFO, "Code reçu : " + code + " et nom du service : " + serviceName);
        interpreter.sendOAuthCode(code, serviceName);

        return CorsAdder.addCors(Response.ok()).build();
    }

    @Override
    public Response retrieveOAuthURL(String serviceName) throws NoSuchServiceException {
        try {
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(interpreter.retrieveOAuthURL(serviceName)))
                    .build();
        } catch (Exception e){
            return CorsAdder.addCors(
                    Response.status(Response.Status.OK).entity(new NullResponse()))
                    .build();
        }
    }
}
