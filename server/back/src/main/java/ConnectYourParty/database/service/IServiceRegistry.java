package ConnectYourParty.database.service;

import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IServiceRegistry {
    void addServiceHolder(ServiceHolder service);
    List<ServiceHolder> getServiceHolderFromModule(Module module);
    List<ServiceHolder> getServiceHolder();

    void remove(ServiceHolder serviceHolder);
    void clean();
}
