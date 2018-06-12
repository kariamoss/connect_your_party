package connectYourParty.modulesLogic.service;

import connectYourParty.businessObjects.service.ServiceHolder;

public interface Subscriber {
    void onAdd(ServiceHolder holder);
    void onRemove(ServiceHolder holder);
    void onUnsubscribe();

}
