package ConnectYourParty.database.token;

import ConnectYourParty.businessObjects.Token;

import javax.ejb.Local;

@Local
public interface ITokenDatabase {

    void addToken(Token token);
    void removeToken(Token token);
    void clean();

}
