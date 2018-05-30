package ConnectYourParty.webInterface.photo;

import ConnectYourParty.modulesLogic.chooser.PhotoChooser;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.modulesLogic.interpreter.PhotoInterpreter;
import ConnectYourParty.requestObjects.photo.UploadRequest;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import ConnectYourParty.webInterface.CorsAdder;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoModule implements IPhotoModule {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());


    @Override
    public Response addPhoto(MultipartBody body) {
        try {

            String name = body.getAttachmentObject("name",String.class);
            InputStream input = body.getAttachment("file").getDataHandler().getInputStream();
            String service = body.getAttachmentObject("service",String.class);

            PhotoInterpreter interpreter = new PhotoInterpreter();
            interpreter.addPhoto(input, name,service);

            return CorsAdder.addCors(Response.ok()).build();
        } catch (AddPhotoErrorException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
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
        CorsAdder.addCors(response);
        return response.build();
    }

    public File findPhoto(){
        URL path = this.getClass().getClassLoader().getResource("image.jpg");
        return new File(path.getPath());
    }

    @Override
    public Response getPhotoServices() {
        PhotoChooser photoChooser = new PhotoChooser();
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(photoChooser.getServices()))
                .build();
    }
}
