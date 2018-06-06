package ConnectYourParty.modulesLogic.common.serviceUser;

import ConnectYourParty.DropboxService;
import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.services.IServiceOAuth;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ServiceUser implements IServiceUser {

    private List<IServiceOAuth> serviceList;

    @PostConstruct
    public void init(){
        serviceList = new ArrayList<>();
        serviceList.add(new DropboxService());
    }

    @Override
    public Token getToken(String code, String serviceName) throws NoSuchServiceException {
        Token t = new Token(code, serviceName);
        t.setAccessToken(this.getService(serviceName).updateToken(code));

        return t;
    }

    @Override
    public URL getOAuthURL(String serviceName) throws NoSuchServiceException {
        return this.getService(serviceName).getOAuthUrl();
    }

    private IServiceOAuth getService(String serviceName) throws NoSuchServiceException {
        for(IServiceOAuth service : serviceList){
            if(service.getServiceName().equals(serviceName)){
                return service;
            }
        }
        throw new NoSuchServiceException();
    }
}
