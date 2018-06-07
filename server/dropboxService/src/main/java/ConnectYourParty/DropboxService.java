package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.services.IServiceOAuth;
import ConnectYourParty.services.photo.IPhotoService;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import org.json.*;

public class DropboxService implements IPhotoService, IServiceOAuth {

    // private final String token = "3R_uMjczZjAAAAAAAAAAfB2FMQjheEyR89fJsWHUv7pVSI-yV1ai3w4FlsK5M9fP";
    private DbxClientV2 client;


    public DropboxService() {
        // DbxRequestConfig conf = DbxRequestConfig.newBuilder("coty").build();
        // this.client = new DbxClientV2(conf, token);
    }

    @Override
    public void addPhoto(byte[] photo, String path, Optional<TokenService> token) throws AddPhotoErrorException {
        DbxRequestConfig conf = DbxRequestConfig.newBuilder("coty").build();
        token.ifPresent(tokenService -> this.client = new DbxClientV2(conf, tokenService.getAccessToken()));
        try {
            InputStream in = new ByteArrayInputStream(photo);

            this.client.files().uploadBuilder(path).uploadAndFinish(in);

        } catch (Exception e) {
            throw new AddPhotoErrorException("Can't add photo " + path + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public byte[] getPhoto(String path, Optional<TokenService> token) throws RetrievePhotoErrorException {
        DbxRequestConfig conf = DbxRequestConfig.newBuilder("coty").build();
        token.ifPresent(tokenService -> this.client = new DbxClientV2(conf, tokenService.getAccessToken()));
        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            client.files().download(path).download(out);
            byte[] buff = out.toByteArray();
            out.close();

            return buff;
        } catch (Exception e) {
            throw new RetrievePhotoErrorException("Cannot retrieve photo " + path + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void removePhoto(String path, Optional<TokenService> token) throws CannotDeletePhotoException {
        DbxRequestConfig conf = DbxRequestConfig.newBuilder("coty").build();
        token.ifPresent(tokenService -> this.client = new DbxClientV2(conf, tokenService.getAccessToken()));
        try {
            this.client.files().deleteV2(path);
        } catch (Exception e) {
            throw new CannotDeletePhotoException("Cannot delete photo " + path + "\n" + Arrays.toString(e.getStackTrace()));
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
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public URL getOAuthUrl() {
        try {
            return new URL("https://www.dropbox.com/oauth2/authorize");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public URL getOAuthToken() {
        try {
            return new URL("https://api.dropboxapi.com/oauth2/token");
        } catch (Exception e) {
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

    @Override
    public TokenService updateToken(String oAuthCode) {
        StringBuilder result = new StringBuilder();
        JSONObject resultJSON = new JSONObject();
        String parameters = "code=" + oAuthCode +
                "&grant_type=authorization_code" +
                "&client_id=qmoepnnfjdergql" +
                "&client_secret=lwlzexl2nnypmlq" +
                "&redirect_uri=http://localhost:4200/authentication/?service=" + this.getServiceName();
        URL url = getOAuthToken();
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            resultJSON = new JSONObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TokenService(oAuthCode, resultJSON.getString("access_token"), oAuthCode);

    }
}
