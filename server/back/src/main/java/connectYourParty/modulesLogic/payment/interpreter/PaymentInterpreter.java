package connectYourParty.modulesLogic.payment.interpreter;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.database.user.IUserRegistry;
import connectYourParty.exception.NoSuchUserException;
import connectYourParty.modulesLogic.payment.serviceUser.IPaymentServiceUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Stateless
public class PaymentInterpreter implements IPaymentInterpreter {

    @EJB
    private IPaymentServiceUser paymentServiceUser;

    @EJB
    private IUserRegistry userRegistry;

    @Override
    public URL pay(String target, double amount, String serviceName, String userId) throws NoSuchUserException {
        Optional<Token> token;

        User user = userRegistry.getUserById(userId);

        if (user.getToken(serviceName).isPresent())
            token = Optional.of(user.getToken(serviceName).get());
        else token = Optional.empty();

        List<URL> urls = paymentServiceUser.pay(target, amount, serviceName, token);

        URL toSave = findURLToSave(urls);

        if(token.isPresent()){
            token.get().addAdditionalInfo("execute",toSave.toString());
            this.userRegistry.updateToken(token.get());
        }

        return findURLToReturn(urls);
    }

    private URL findURLToSave(List<URL> urls){
        for(URL url : urls){
            if (urls.toString().contains("execute")){
                return url;
            }
        }
        return null;
    }

    private URL findURLToReturn(List<URL> urls){
        for(URL url : urls){
            if (!url.toString().contains("execute")){
                return url;
            }
        }
        return null;
    }
}
