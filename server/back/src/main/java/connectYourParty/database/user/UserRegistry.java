package connectYourParty.database.user;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.database.DbMock;
import connectYourParty.exception.NoSuchUserException;

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
        initCheck();
        user.addToken(token);
        manager.merge(user);
    }

    @Override
    public User getUserById(String id) throws NoSuchUserException {
        initCheck();
        User user =  this.manager.find(User.class,id);

        if(user == null){
            throw new NoSuchUserException();
        }

        return user;
    }

    public void initCheck(){
        if(this.getUserList().size() == 0){
            manager.persist(DbMock.user);
            manager.persist(DbMock.user1);
            manager.persist(DbMock.user2);
            manager.persist(DbMock.user3);
        }
    }


}
