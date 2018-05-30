package ConnectYourParty;

import ConnectYourParty.services.photo.IPhotoService;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DropboxService implements IPhotoService{

    private final String token = "3R_uMjczZjAAAAAAAAAAfB2FMQjheEyR89fJsWHUv7pVSI-yV1ai3w4FlsK5M9fP";
    private DbxClientV2 client;


    public DropboxService(){
        DbxRequestConfig conf = DbxRequestConfig.newBuilder("coty").build();
        this.client = new DbxClientV2(conf,token);
    }

    @Override
    public String addPhoto(byte[] photo,String path) {
        try {
            InputStream in = new ByteArrayInputStream(photo);

            this.client.files().uploadBuilder(path).uploadAndFinish(in);

        } catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @Override
    public byte[] getPhotos(String photo) {
        try {
            InputStream stream = client.files().download(photo).getInputStream();

            byte[] buff = new byte[stream.available()];
            stream.read(buff);

            return buff;
        } catch (Exception e){
            e.printStackTrace();
        }


        return new byte[0];
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

    public boolean remove(String path) {
        try {
            this.client.files().deleteV2(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
