package ConnectYourParty.modulesLogic.common.serviceUser;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.requestObjects.request.OAuthHolder;
import ConnectYourParty.services.IServiceOAuth;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ServiceUser implements IServiceUser {

    @EJB
    private IServiceRegistry registry;


    @Override
    public Token getToken(String code, String serviceName) throws NoSuchServiceException {
        TokenService tokenService = this.getService(serviceName).getToken(code);
        Token t = new Token(code, serviceName, tokenService.getAccessToken());
        if (tokenService.getRefreshToken() != null) {
            t.setRefreshToken(tokenService.getRefreshToken());
        }
        return t;
    }

    @Override
    public OAuthHolder getOAuthURL(String serviceName) throws NoSuchServiceException {
        IServiceOAuth service =  this.getService(serviceName);

        return new OAuthHolder(service.getAppKey(),service.getOAuthUrl().toString());
    }

    private IServiceOAuth getService(String serviceName) throws NoSuchServiceException {
        List<ServiceHolder> res = this.registry.getServiceHolder();

        for(ServiceHolder holder : res){
            try{
                IServiceOAuth service = (IServiceOAuth) holder.getService();
                if(service.getServiceName().equals(serviceName)){
                    return service;
                }
            } catch (Exception e){
            }
        }

        throw new NoSuchServiceException();
    }
}
