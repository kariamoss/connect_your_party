package ConnectYourParty.webInterface.photo;

import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.modulesLogic.chooser.PhotoChooser;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.modulesLogic.interpreter.PhotoInterpreter;
import ConnectYourParty.requestObjects.photo.UploadRequest;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import ConnectYourParty.webInterface.CorsAdder;

import javax.servlet.ServletOutputStream;
import javax.ws.rs.FormParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoModule implements IPhotoModule {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());


    @Override
    public Response addPhoto(MultipartBody body) {
        try {

            String name = "/" + body.getAttachmentObject("name",String.class);
            InputStream input = body.getAttachment("file").getDataHandler().getInputStream();
            String service = body.getAttachmentObject("service",String.class);

            PhotoInterpreter interpreter = new PhotoInterpreter();
            interpreter.addPhoto(input, name,service);

            return CorsAdder.addCors(Response.ok()).build();
        } catch (AddPhotoErrorException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (PhotoAlreadyExistException e){
            logger.log(Level.SEVERE, "photo already in the system");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @Override
    public Response getPhotoList() {
        PhotoInterpreter photoInterpreter = new PhotoInterpreter();
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(photoInterpreter.getPhotoList()))
                .build();
    }

    @Override
    public Response getPhoto(String path) {
        PhotoInterpreter photoInterpreter = new PhotoInterpreter();
        byte[] photo;
        try {
            photo = photoInterpreter.getPhoto("/" + path);
        } catch (RetrievePhotoErrorException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (NoSuchServiceException e){
            logger.log(Level.SEVERE, "service doesn't exist ");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (NoSuchPhotoException e){
            logger.log(Level.SEVERE, "photo doesn't exist");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(photo))
                .build();
    }

    @Override
    public Response getPhotoServices() {
        PhotoChooser photoChooser = new PhotoChooser();
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(photoChooser.getServices()))
                .build();
    }
}
