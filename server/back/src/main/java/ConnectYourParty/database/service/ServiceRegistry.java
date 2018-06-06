package ConnectYourParty.database.service;

import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class ServiceRegistry implements IServiceRegistry{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addServiceHolder(ServiceHolder service) {
        entityManager.persist(service);
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
    public void remove(ServiceHolder serviceHolder){
        serviceHolder = entityManager.merge(serviceHolder);
        entityManager.remove(serviceHolder);

    }

    @Override
    public void clean() {
        for(ServiceHolder holder : getServiceHolder()){
            remove(holder);
        }
    }
}
