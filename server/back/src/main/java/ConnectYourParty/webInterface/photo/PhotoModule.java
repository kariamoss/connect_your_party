package ConnectYourParty.webInterface.photo;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.exception.photo.NoSuchPhotoException;
import ConnectYourParty.exception.NoSuchServiceException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.modulesLogic.photo.interpreter.IPhotoInterpreter;
import ConnectYourParty.requestObjects.photo.PhotoAdderBody;
import ConnectYourParty.webInterface.Module;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import ConnectYourParty.webInterface.CorsAdder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PhotoModule implements IPhotoModule {

    Logger logger = Logger.getLogger(PhotoModule.class.getName());

    @EJB
    private IPhotoInterpreter interpreter;

    @Override
    public Response addPhoto(MultipartBody body) {
        try {
            logger.log(Level.INFO,"starting photo upload");
            PhotoAdderBody photo = parseBody(body);
            String name = photo.getName();
            InputStream input = photo.getInput();
            String service = photo.getService();


            interpreter.addPhoto(input, name,service, DbMock.user.getName());
            logger.log(Level.INFO,"finishing photo upload");
            return CorsAdder.addCors(Response.ok()).build();
        } catch (AddPhotoErrorException e) {
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getMessage());
            return CorsAdder.corsResponse().status(Response.Status.NOT_ACCEPTABLE).build();
        }
        catch (Exception e){
            logger.log(Level.SEVERE, e.toString());

            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()) );
            return CorsAdder.corsResponse().status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @Override
    public Response getPhotoList() {
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(interpreter.getPhotoList()))
                .build();
    }

    @Override
    public Response getPhoto(String event, String name) {
        byte[] photo;
        try {
            photo = interpreter.getPhoto(event + "/" + name);
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
        return CorsAdder.addCors(
                Response.status(Response.Status.OK).entity(interpreter.getServices()))
                .build();
    }

    private PhotoAdderBody parseBody(MultipartBody body) throws IOException{
        PhotoAdderBody photo = new PhotoAdderBody();
        photo.setName(body.getAttachmentObject("name",String.class));
        photo.setInput( body.getAttachment("file").getDataHandler().getInputStream());
        photo.setService( body.getAttachmentObject("service",String.class));
        photo.setUserId(DbMock.user.getName());

        if(!photo.check()){
            photo.setName(body.getAttachment("name").getDataHandler().getContent().toString());
            photo.setService(body.getAttachment("service").getDataHandler().getContent().toString());
        }

        return photo;
    }
}
