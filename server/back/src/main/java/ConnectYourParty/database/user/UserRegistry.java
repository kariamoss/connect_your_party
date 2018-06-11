package ConnectYourParty.database.user;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.database.DbMock;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class UserRegistry implements IUserRegistry{

    @PersistenceContext private EntityManager manager;

    @Override
    public List<User> getUserList() {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root =  criteria.from(User.class);
        criteria.select(root);
        TypedQuery<User> query = manager.createQuery(criteria);

        if(query.getResultList().size() == 0){
            manager.persist(DbMock.user);
            manager.persist(DbMock.user1);
            manager.persist(DbMock.user2);
            manager.persist(DbMock.user3);
        }
        return query.getResultList();
    }

    @Override
    public void addToken(User user, Token token){
        user.addToken(token);
        manager.merge(token);
    }

    @Override
    public User getUserById(String id) {
        return this.manager.find(User.class,id);
    }


}
