package ConnectYourParty.database.token;

import ConnectYourParty.businessObjects.Token;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface ITokenDatabase {

    void addToken(Token token);
    void removeToken(Token token);
    Optional<Token> getTokenFromServiceName(String serviceName);
    void clean();

}
