package connectYourParty.database.user;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import connectYourParty.exception.NoSuchUserException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IUserRegistry {

    List<User> getUserList();
    void addToken(User user, Token token);
    User getUserById(String id) throws NoSuchUserException;
}
