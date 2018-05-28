package ConnectYourParty.webInterface.photo;

import ConnectYourParty.chooser.PhotoChooser;
import ConnectYourParty.requestObjects.Photo;
import ConnectYourParty.services.photo.IPhotoService;
import org.json.JSONObject;
import ConnectYourParty.webInterface.WebInterfaceHelper;

import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.Response.serverError;

import javax.ws.rs.core.Response;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoModule implements IPhotoModule {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());


    @Override
    public Response addPhoto(Photo photo) {
        logger.log(Level.INFO,photo.hello);
        PhotoChooser photoChooser = new PhotoChooser();
        //IPhotoService photoService = photoChooser.getService();
        //photoService.addPhoto(url);
        return Response.ok().build();

    }

    @Override
    public Response getPhotos() {
        //PhotoChooser photoChooser = new PhotoChooser();
        //IPhotoService photoService = photoChooser.getService();
        //photoService.getPhotos(url);
        File photo = findPhoto();
        Response.ResponseBuilder response = Response.ok((Object) photo);
        response.header("Content-Disposition", "attachment;filename=photo.jpg");
        return response.build();
    }

    public File findPhoto(){
        return new File("/home/luquamateo/si4/connectyourparty/image.jpg");
    }

    @Override
    public Response getPhotoServices() {
        PhotoChooser photoChooser = new PhotoChooser();
        return Response.status(Response.Status.OK).entity(photoChooser.getServices()).build();
    }
}
