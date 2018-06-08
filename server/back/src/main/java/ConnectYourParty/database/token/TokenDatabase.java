package ConnectYourParty.database.token;

import ConnectYourParty.businessObjects.Token;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
public class TokenDatabase implements ITokenDatabase {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addToken(Token token) {
        if (this.entityManager.find(Token.class, token.getServiceName()) == null) {
            this.entityManager.persist(token);
            this.entityManager.flush();
            return;
        }
        this.entityManager.merge(token);
        this.entityManager.flush();
    }

    @Override
    public void removeToken(Token token) {
        token = entityManager.merge(token);
        entityManager.remove(token);
    }

    @Override
    public Optional<Token> getTokenFromServiceName(String serviceName) {
        Token token = this.entityManager.find(Token.class, serviceName);
        if (token == null) {
            return Optional.empty();
        }
        return Optional.of(token);
    }

    @Override
    public void clean() {

    }
}
