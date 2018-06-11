package ConnectYourParty.webInterface.user;

import ConnectYourParty.webInterface.CorsAdder;

import javax.ws.rs.core.Response;

public class UserRoute implements IUserRoute {

    @Override
    public Response getUserList() {
        return CorsAdder.corsResponse().build();
    }
}
