package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.services.photo.IPhotoService;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            InputStream stream = client.files().download(path).getInputStream();

            byte[] buff = new byte[stream.available()];
            stream.read(buff);

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
        return "dropbox";
    }

    @Override
    public URL getServiceIcon() {
        try {
            return new URL("https://www.iconfinder.com/icons/173882/download/png/128");
        } catch (Exception e){
            return null;
        }
    }
}
