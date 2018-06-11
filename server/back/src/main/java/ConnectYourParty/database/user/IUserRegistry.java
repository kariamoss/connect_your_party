package ConnectYourParty.database.user;

import ConnectYourParty.businessObjects.Token;
import ConnectYourParty.businessObjects.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IUserRegistry {

    List<User> getUserList();
    void addToken(User user, Token token);
    User getUserById(String id);
}
