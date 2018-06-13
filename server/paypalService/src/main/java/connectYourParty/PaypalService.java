package connectYourParty;

import connectYourParty.objects.TokenEntry;
import connectYourParty.objects.TokenService;
import connectYourParty.services.IServiceOAuth;
import connectYourParty.services.payment.IPaymentService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaypalService implements IPaymentService, IServiceOAuth {

    Logger logger = Logger.getLogger(PaypalService.class.getName());

    @Override
    public List<URL> buildPayment(String target, double amount, Optional<TokenService> token) {
        StringBuilder result = new StringBuilder();
        JSONObject body = new JSONObject();
        body.put("intent", "sale");
        body.put("redirect_urls", new JSONObject()
                .put("return_url", "http://localhost:4200/processPayment")
                .put("cancel_url", "http://localhost:4200/cancelPayment"));
        body.put("payer", new JSONObject().put("payment_method", "paypal"));
        body.put("transactions", new JSONArray()
                .put(new JSONObject()
                        .put("amount", new JSONObject()
                                .put("total", String.valueOf(amount))
                                .put("currency", "EUR"))
                        .put("payee", new JSONObject()
                                .put("email", target))
                        .put("description", "Remboursement via connect your party.")));

        logger.log(Level.INFO, "POST body : " + body.toString());
        JSONObject resultJSON = new JSONObject();
        byte[] postData = body.toString().getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            URL url = new URL("https://api.sandbox.paypal.com/v1/payments/payment");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            String encoded = Base64.getEncoder().encodeToString((this.getAppKey() + ":" + this.getAppSecret()).getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Bearer " + token.get().getAccessToken());
            conn.setRequestProperty("Content-Type", "application/json");
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

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
                resultJSON = new JSONObject(result.toString());
                logger.log(Level.INFO, "POST body : " + resultJSON.toString(1));

            } else {
                throw new RuntimeException("Response from service server : " + responseCode);
            }

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        JSONArray links = resultJSON.getJSONArray("links");
        List<URL> urls = new ArrayList<>();
        for (int i = 0; i < links.length(); i++) {
            JSONObject object = links.getJSONObject(i);
            if (!object.getString("rel").equals("self")) {
                try {
                    urls.add(new URL(object.getString("href")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        return urls;
    }

    @Override
    public void confirm(String payerId, Optional<TokenService> token) {
        StringBuilder result = new StringBuilder();
        JSONObject body = new JSONObject();
        body.put("payer_id", payerId);
        JSONObject resultJSON = new JSONObject();
        byte[] postData = body.toString().getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            URL url = new URL(token.get().getAdditionalInfos().get(TokenEntry.EXECUTE.key));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token.get().getAccessToken());
            conn.setRequestProperty("Content-Type", "application/json");
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
    }

    @Override
    public URL getOAuthUrl() {
        try {
            return new URL("http://localhost:4200/authentication/?service=" + this.getServiceName() + "&code=josuepd");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    public String getAppSecret() {
        return "EEN-julrYuKSGUObBYhxU_7U5wkTiTjCsV_g0W-hcvfPM7czBNLHl63VUmQ2JEh-5twzGU6ECJfj5vvU";
    }

    @Override
    public TokenService generateToken(String oAuthCode) {
        StringBuilder result = new StringBuilder();
        JSONObject resultJSON = new JSONObject();
        String parameters = "grant_type=client_credentials";
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            URL url = new URL("https://api.sandbox.paypal.com/v1/oauth2/token");
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
