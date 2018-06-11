package ConnectYourParty.webInterface.user;

import ConnectYourParty.businessObjects.User;
import ConnectYourParty.database.user.IUserRegistry;
import ConnectYourParty.requestObjects.UserHolder;
import ConnectYourParty.webInterface.CorsAdder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class UserRoute implements IUserRoute {

    @EJB
    private IUserRegistry userRegistry;

    @Override
    public Response getUserList() {
        List<User> users = userRegistry.getUserList();

        UserHolder[] arr = new UserHolder[users.size()];

        for(int i = 0;i<users.size();i++){
            arr[i] = users.get(i).toHolder();
        }


        return CorsAdder.corsResponse().entity(arr).build();
    }
}
