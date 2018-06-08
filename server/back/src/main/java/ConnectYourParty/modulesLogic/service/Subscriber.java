package ConnectYourParty.modulesLogic.service;

import ConnectYourParty.businessObjects.service.ServiceHolder;

public interface Subscriber {
    void onAdd(ServiceHolder holder);
    void onRemove(ServiceHolder holder);
    void onUnsubscribe();

}
