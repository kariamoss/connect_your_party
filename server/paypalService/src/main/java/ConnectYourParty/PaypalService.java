package ConnectYourParty;

import ConnectYourParty.objects.TokenService;
import ConnectYourParty.services.IServiceOAuth;
import ConnectYourParty.services.payment.IPaymentService;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaypalService implements IPaymentService, IServiceOAuth {

    Logger logger = Logger.getLogger(PaypalService.class.getName());

    @Override
    public URL buildPayment(String target, double amount, Optional<TokenService> token) {
        return null;
    }

    @Override
    public void confirm(String payerId, Optional<TokenService> token) {

    }

    @Override
    public URL getOAuthUrl() {
        try {
            return new URL("https://www.sandbox.paypal.com/signin/authorize?client_id=AU_e2nxXR4S3nZdpkI6rdqQGLbnxEXbL1glsW9jYF4Vvd_vcvxkv2X6chqA5kzdZLXxpwIGOzFr4VDaC" +
                    "&response_type=code" +
                    "&scope=openid" +
                    "&redirect_uri=http://localhost:4200/authentication/?service=" + this.getServiceName());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    public URL getOAuthToken() {
        try {
            return new URL("https://api.sandbox.paypal.com/v1/identity/openidconnect/token");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    public String getAppSecret() {
        return "EEN-julrYuKSGUObBYhxU_7U5wkTiTjCsV_g0W-hcvfPM7czBNLHl63VUmQ2JEh-5twzGU6ECJfj5vvU";
    }

    @Override
    public TokenService getToken(String oAuthCode) {
        StringBuilder result = new StringBuilder();
        JSONObject resultJSON = new JSONObject();
        String parameters = "grant_type=client_credentials";
        URL url = getOAuthToken();
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            String encoded = Base64.getEncoder().encodeToString((this.getAppKey() + ":" + this.getAppSecret()).getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Basic " + encoded);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            logger.log(Level.INFO, "POST response code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
                resultJSON = new JSONObject(result.toString());
            } else {
                throw new RuntimeException("Response from service server : " + responseCode);
            }

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return new TokenService("", resultJSON.getString("access_token"), "");
    }

    @Override
    public TokenService refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public String getAppKey() {
        return "AU_e2nxXR4S3nZdpkI6rdqQGLbnxEXbL1glsW9jYF4Vvd_vcvxkv2X6chqA5kzdZLXxpwIGOzFr4VDaC";
    }

    @Override
    public String getServiceName() {
        return "Paypal";
    }

    @Override
    public URL getServiceIcon() {
        try {
            return new URL("http://1000logos.net/wp-content/uploads/2017/05/emblem-Paypal.jpg");
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
