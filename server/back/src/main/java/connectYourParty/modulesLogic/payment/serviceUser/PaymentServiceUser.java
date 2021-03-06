package connectYourParty.modulesLogic.payment.serviceUser;

import connectYourParty.objects.TokenEntry;
import connectYourParty.requestObjects.photo.PhotoServiceHolder;
import connectYourParty.services.IService;
import connectYourParty.services.payment.IPaymentService;
import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.database.service.IServiceRegistry;
import connectYourParty.exception.NoSuchServiceException;
import connectYourParty.modulesLogic.service.Subscriber;
import connectYourParty.objects.TokenService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.net.URL;
import java.util.*;

@Stateless
public class PaymentServiceUser implements IPaymentServiceUser, Subscriber {

    @EJB
    private IServiceRegistry serviceRegistry;

    private Collection<ServiceHolder> servicePaymentList;

    private Map<ServiceHolder, Integer> ids;

    private Module module = Module.PAYMENT;

    @PostConstruct
    public void init() {
        servicePaymentList = new HashSet<>();
        List<ServiceHolder> serviceHolders = this.serviceRegistry.getServiceHolderFromModule(Module.PAYMENT);
        ids = new HashMap<>();

        for(ServiceHolder holder : serviceHolders){
            servicePaymentList.add(holder);
            ids.put(holder, holder.getId());
        }

        this.serviceRegistry.subscribe(this, this.module);
    }

    @Override
    public List<PhotoServiceHolder> getServiceList() {
        List<PhotoServiceHolder> arr = new ArrayList<>();
        for (ServiceHolder serv : servicePaymentList) {
            try {
                IService service = serv.getService();
                arr.add(new PhotoServiceHolder(service.getServiceName(),
                        service.getServiceIcon().getHost() + service.getServiceIcon().getPath()));
            } catch (Exception e){

            }
        }

        return arr;
    }

    @Override
    public List<URL> pay(String target, double amount, String serviceName, Optional<Token> token) {
        try {
            return this.getService(serviceName).buildPayment(target, amount, Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(), token.get().getRefreshToken())));
        } catch (NoSuchServiceException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void confirm(String payerId,Optional<Token> tok,String serviceName) throws NoSuchServiceException {
        Optional<TokenService> token;
        if(tok.isPresent()){
            token = Optional.of(new TokenService(tok.get().getCode(),tok.get().getAccessToken(),tok.get().getRefreshToken()));
            token.get().getAdditionalInfos().put(TokenEntry.EXECUTE.key, tok.get().getInfo(TokenEntry.EXECUTE.key));
        } else {
            token = Optional.empty();
        }

        this.getService(serviceName).confirm(payerId,token);
    }

    private IPaymentService getService(String serviceName) throws NoSuchServiceException {

        for (ServiceHolder holder : servicePaymentList) {
            try {
                IPaymentService service = (IPaymentService) holder.getService();
                if (service.getServiceName().equals(serviceName)) {
                    return service;
                }
            } catch (Exception e){

            }
        }

        throw new NoSuchServiceException();
    }

    @Override
    public void onAdd(ServiceHolder holder) {
        try {
            servicePaymentList.add(holder);
            ids.put(holder, holder.getId());
        } catch (Exception e) {

        }
    }

    @Override
    public void onRemove(ServiceHolder holder) {
        try {
            servicePaymentList.remove(holder);
            ids.remove(holder);
        } catch (Exception e) {

        }
    }

    @Override
    public void onUnsubscribe() {
        this.serviceRegistry.subscribe(this, this.module);
    }
}
