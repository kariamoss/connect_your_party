package ConnectYourParty.modulesLogic.payment.serviceUser;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.database.service.IServiceRegistry;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.modulesLogic.service.Subscriber;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.services.payment.IPaymentService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
        List<ServiceHolder> serviceHolders = this.serviceRegistry.getServiceHolder();
        ids = new HashMap<>();

        for(ServiceHolder holder : serviceHolders){
            servicePaymentList.add(holder);
            ids.put(holder, holder.getId());
        }

        this.serviceRegistry.subscribe(this, this.module);
    }

    @Override
    public void pay(String target, double amount, String serviceName, Optional<Token> token) {
        try {
            this.getService(serviceName).buildPayment(target, amount, Optional.of(new TokenService(token.get().getCode(), token.get().getAccessToken(), token.get().getRefreshToken())));
        } catch (NoSuchServiceException e) {
            e.printStackTrace();
        }
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
