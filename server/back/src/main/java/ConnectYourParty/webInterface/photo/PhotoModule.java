package ConnectYourParty.webInterface.photo;

import ConnectYourParty.chooser.PhotoChooser;
import ConnectYourParty.requestObjects.photo.UploadRequest;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

import java.io.*;
import java.net.URL;
import java.util.logging.Logger;

public class PhotoModule implements IPhotoModule {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());


    @Override
    public Response addPhoto(
            @Multipart(value = "file") InputStream input,
            @Multipart(value = "name") String name
    ) {
        try {
            PhotoChooser chooser = new PhotoChooser();
            chooser.addPhoto(input, name);
            return Response.ok().build();
        } catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

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
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        return new File(path.getPath());
    }

    @Override
    public Response getPhotoServices() {
        PhotoChooser photoChooser = new PhotoChooser();
        return Response.status(Response.Status.OK).entity(photoChooser.getServices()).build();
    }
}
