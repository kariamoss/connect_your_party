package ConnectYourParty.database.token;

import ConnectYourParty.businessObjects.Token;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TokenDatabase implements ITokenDatabase {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addToken(Token token) {
        this.entityManager.persist(token);
        this.entityManager.flush();
    }

    @Override
    public void removeToken(Token token) {
        token = entityManager.merge(token);
        entityManager.remove(token);
    }

    @Override
    public void clean() {

    }
}
