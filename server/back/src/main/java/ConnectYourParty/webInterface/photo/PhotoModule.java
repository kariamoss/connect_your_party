package ConnectYourParty.webInterface.photo;

import ConnectYourParty.chooser.PhotoChooser;
import ConnectYourParty.services.photo.IPhotoService;
import ConnectYourParty.webInterface.WebInterfaceHelper;

import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.Response.serverError;

public class PhotoModule implements IPhotoModule {

    @Override
    public String addPhoto() {
        PhotoChooser photoChooser = new PhotoChooser();
        //photoService.addPhoto(url);
        return "salut";
    }

    @Override
    public String getPhotos() {
        PhotoChooser photoChooser = new PhotoChooser();
        //photoService.getPhotos(url);
        return "Vla tes photos";
}

    @Override
    public Response getPhotoServices() {
        PhotoChooser photoChooser = new PhotoChooser();
        return Response.status(Response.Status.OK).entity(photoChooser.getServices()).build();
    }
}
