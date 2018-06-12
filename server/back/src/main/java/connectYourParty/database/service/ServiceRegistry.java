package connectYourParty.database.service;

import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.modulesLogic.service.Subscriber;
import connectYourParty.modulesLogic.service.Updater;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Singleton
public class ServiceRegistry implements IServiceRegistry{

    @PersistenceContext
    private EntityManager entityManager;

    private Updater updater;

    @PostConstruct
    public void init(){
        updater = new Updater();
    }

    @Override
    public void addServiceHolder(ServiceHolder service) {
        entityManager.persist(service);
        updater.add(service,service.getModule());
    }

    @Override
    public List<ServiceHolder> getServiceHolderFromModule(Module module) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ServiceHolder> crit = builder.createQuery(ServiceHolder.class);

        Root<ServiceHolder> root = crit.from(ServiceHolder.class);

        crit.select(root).where(root.get("module").in(module));

        TypedQuery<ServiceHolder> query = entityManager.createQuery(crit);

        return query.getResultList();
    }

    @Override
    public List<ServiceHolder> getServiceHolder() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ServiceHolder> crit = builder.createQuery(ServiceHolder.class);

        Root<ServiceHolder> root = crit.from(ServiceHolder.class);

        crit.select(root);

        TypedQuery<ServiceHolder> query = entityManager.createQuery(crit);

        return query.getResultList();
    }

    @Override
    public void subscribe(Subscriber subs, Module module) {
        updater.subscribe(subs,module);
    }

    @Override
    public void unSubscribe(Subscriber subs, Module module) {
        updater.subscribe(subs,module);
    }

    @Override
    public void remove(ServiceHolder serviceHolder){
        serviceHolder = entityManager.merge(serviceHolder);
        entityManager.remove(serviceHolder);
        updater.remove(serviceHolder,serviceHolder.getModule());
    }

    @Override
    public void clean() {
        for(ServiceHolder holder : getServiceHolder()){
            remove(holder);
        }
    }


}
