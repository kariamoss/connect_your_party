package ConnectYourParty.webInterface;

import ConnectYourParty.chooser.PhotoChooser;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.Response.serverError;

public class WebInterfaceHelper {

    public static Optional<Response> checkEventId(String eventId){
        int idResponse = 0;

        if(eventId == null || eventId.trim().length() == 0) {
            return Optional.ofNullable(serverError().entity("EventID cannot be null").build());
        }
        try{
            idResponse = Integer.parseInt(eventId);
        }catch (NumberFormatException e){
            return Optional.ofNullable(serverError().entity("EventID must be an integer").build());
        }

        // TODO check event existence
        return Optional.empty();
    }
}
