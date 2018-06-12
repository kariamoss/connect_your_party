package connectYourParty.webInterface;

import connectYourParty.database.DbMock;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.modulesLogic.common.interpreter.IInterpreter;
import connectYourParty.requestObjects.request.NullResponse;

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
    public Response sendOAuthCode(String code, String serviceName, String userId) throws NoSuchServiceException {
        // JSONObject object = new JSONObject(text);
        try {
            logger.log(Level.INFO, "Code reçu : " + code + " et nom du service : " + serviceName);
            interpreter.sendOAuthCode(code, serviceName, DbMock.user.getName());
            return CorsAdder.addCors(Response.ok()).build();
        } catch (Exception e){
            return CorsAdder.corsResponse().status(Response.Status.NOT_ACCEPTABLE).build();
        }
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
