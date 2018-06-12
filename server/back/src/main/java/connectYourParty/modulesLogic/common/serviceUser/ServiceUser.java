package connectYourParty.modulesLogic.common.serviceUser;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.objects.TokenService;
import connectYourParty.requestObjects.request.OAuthHolder;
import connectYourParty.services.IServiceOAuth;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ServiceUser implements IServiceUser {

    @EJB
    private IServiceRegistry registry;


    @Override
    public Token getToken(String code, String serviceName) throws NoSuchServiceException {
        TokenService tokenService = this.getService(serviceName).generateToken(code);
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
