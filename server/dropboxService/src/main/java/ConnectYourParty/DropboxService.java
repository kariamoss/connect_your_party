package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.services.photo.IPhotoService;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;


import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class DropboxService implements IPhotoService{

    private final String token = "3R_uMjczZjAAAAAAAAAAfB2FMQjheEyR89fJsWHUv7pVSI-yV1ai3w4FlsK5M9fP";
    private DbxClientV2 client;


    public DropboxService(){
        DbxRequestConfig conf = DbxRequestConfig.newBuilder("coty").build();
        this.client = new DbxClientV2(conf,token);
    }

    @Override
    public void addPhoto(byte[] photo,String path) throws AddPhotoErrorException {
        try {
            InputStream in = new ByteArrayInputStream(photo);

            this.client.files().uploadBuilder(path).uploadAndFinish(in);

        } catch (Exception e){
            throw new AddPhotoErrorException("Can't add photo " + path + "\n"+ Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public byte[] getPhoto(String path) throws RetrievePhotoErrorException {
        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            client.files().download(path).download(out);
            byte[] buff = out.toByteArray();
            out.close();

            return buff;
        } catch (Exception e){
            throw new RetrievePhotoErrorException("Cannot retrieve photo " + path + "\n"+ Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void removePhoto(String path) throws CannotDeletePhotoException {
        try {
            this.client.files().deleteV2(path);
        } catch (Exception e) {
            throw new CannotDeletePhotoException("Cannot delete photo " + path+ "\n"+ Arrays.toString(e.getStackTrace()));
        }
    }


    @Override
    public String getServiceName() {
        return "Dropbox";
    }

    @Override
    public URL getServiceIcon() {
        try {
            return new URL("https://image.flaticon.com/icons/png/128/179/179317.png");
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public URL getOAuth() {
        try {
            return new URL("https://www.dropbox.com/oauth2/authorize");
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void setOAuthToken(String oAuthToken) {
        try {
            return new URL("https://api.dropboxapi.com/oauth2/token");
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public String getAppKey() {
        return "qmoepnnfjdergql";
    }

    @Override
    public String getAppSecret() {
        return "lwlzexl2nnypmlq";
    }
}
