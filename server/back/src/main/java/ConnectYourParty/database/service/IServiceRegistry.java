package ConnectYourParty.database.service;

import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;
import ConnectYourParty.modulesLogic.service.Subscriber;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IServiceRegistry {
    void addServiceHolder(ServiceHolder service);
    List<ServiceHolder> getServiceHolderFromModule(Module module);
    List<ServiceHolder> getServiceHolder();

    void subscribe(Subscriber subs, Module module);
    void unSubscribe(Subscriber subs, Module module);

    void remove(ServiceHolder serviceHolder);
    void clean();
}
