package ConnectYourParty;

import ConnectYourParty.exceptions.music.CannotGetUserId;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.TokenService;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
import ConnectYourParty.services.IServiceOAuth;
import ConnectYourParty.services.music.IMusicService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import org.json.*;

public class SpotifyService implements IMusicService, IServiceOAuth {

    public final int searchResults = 10;
    private final String baseURL = "https://api.spotify.com/v1";

    private String token;
    private final String test_refresh_token = "AQAZsfm9j8Hc2EJX_gHAhjAA6sHiXr0e6_xn4HICjEylaNEN_Q_zNbNhMlhiTsQhwMh6uO0snLbRMH1mt6KiJlqL7q76BO_7bFhab3sYTjZtDIEhW_mx1t-53RSxMCuTF6g";

    public SpotifyService() {
    }


    public void updateTestToken() throws GetMusicErrorException {

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String urlParameters = "client_id=" + getAppKey() + "&client_secret=" + getAppSecret() + "&grant_type=refresh_token&refresh_token=" + test_refresh_token;

            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }

            rd.close();
        } catch (Exception e) {
            throw new GetMusicErrorException("Error on request to Spotify Web Service : \n" + Arrays.toString(e.getStackTrace()));
        }

        JSONObject jo = new JSONObject(result.toString());
        token = jo.getString("access_token");
    }

    @Override
    public MusicService getInfoFromId(String id, Optional<TokenService> token) throws GetMusicErrorException {
        return JsonToMusic(GET(baseURL + "/tracks/" + id, token));
    }

    @Override
    public void addMusicFromId(String id, String playlist, Optional<TokenService> token) throws GetMusicErrorException {
        Optional<String> opt = this.getUserId(token);

        if (!opt.isPresent()) {
            throw new GetMusicErrorException();
        }

        String path = this.baseURL + "/" +
                "users/" + opt.get() + "/" +
                "playlists/" + playlist + "/tracks?uris=spotify%3Atrack%3A" + id;
        try {
            this.POST(path, "", token);
        } catch (Exception e) {
            throw new GetMusicErrorException();
        }
    }

    @Override
    public PlaylistService addPlaylist(Optional<TokenService> token) {
        JSONObject body = new JSONObject();

        body.put("name", "Coty");
        body.put("public", false);
        body.put("collaborative", true);

        try {
            String uri;
            Optional<String> opt = this.getUserId(token);
            if (opt.isPresent()) {
                uri = this.baseURL + "/users/" + opt.get() + "/playlists";
            } else {
                throw new CannotGetUserId();
            }

            JSONObject response = new JSONObject(this.POST(uri, body.toString(), token));

            return new PlaylistService(response.getString("id"));
        } catch (Exception e) {
            return new PlaylistService(null);
        }
    }

    @Override
    public List<MusicService> getMusicFromPlaylist(String playlist, Optional<TokenService> token) {
        try {
            Optional<String> opt = this.getUserId(token);

            if (!opt.isPresent()) {
                return new ArrayList<>();
            }


            String path = this.baseURL + "/" +
                    "users/" + opt.get() +
                    "/playlists/" + playlist + "/tracks";


            JSONObject jo = GET(path, token);
            JSONArray arr = jo.getJSONArray("items");

            List<MusicService> list = new ArrayList();
            for (int i = 0; i < arr.length(); i++) {
                list.add(JsonToMusic(arr.getJSONObject(i).getJSONObject("track")));
            }

            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MusicService> searchMusic(String search, Optional<TokenService> token) throws GetMusicErrorException {

        List<MusicService> list = new ArrayList<>();

        String query = null;
        try {
            query = String.format("q=%s&type=track&limit=" + searchResults,
                    URLEncoder.encode(search, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject jo = GET(baseURL + "/search?" + query, token);
        JSONArray arr = jo.getJSONObject("tracks").getJSONArray("items");


        for (int i = 0; i < arr.length(); i++) {
            list.add(JsonToMusic(arr.getJSONObject(i)));
        }

        return list;
    }

    private Optional<String> getUserId(Optional<TokenService> token) {
        try {
            JSONObject response = this.GET(this.baseURL + "/me", token);
            return Optional.of(response.getString("id"));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    private JSONObject GET(String uri, Optional<TokenService> token) throws GetMusicErrorException {

        updateTestToken(); //to remove

        StringBuilder result = new StringBuilder();
        if (token.isPresent()) {
            try {
                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Bearer " + token.get().getAccessToken());
                conn.setRequestProperty("Content-Type", "application/json");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);

                }
                rd.close();
            } catch (Exception e) {
                throw new GetMusicErrorException("Error on request to Spotify Web Service : " + baseURL + uri + "\n" + Arrays.toString(e.getStackTrace()));
            } //catch Exception token expiré(){}
        }
        return new JSONObject(result.toString());
    }

    private String POST(String uri, String body, Optional<TokenService> token) throws Exception {

        StringBuilder result = new StringBuilder();

        if (token.isPresent()) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token.get().getAccessToken());
            conn.getOutputStream().write(body.getBytes("UTF8"));


            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }

        return result.toString();
    }

    private MusicService JsonToMusic(JSONObject jo) {

        String id = jo.getString("id");
        String title = jo.getString("name");
        String author = jo.getJSONArray("artists").getJSONObject(0).getString("name");
        return new MusicService(id, title, author);
    }

    @Override
    public String getServiceName() {
        return "Spotify";
    }

    @Override
    public URL getServiceIcon() {
        try {
            return new URL("http://icons.iconarchive.com/icons/dakirby309/simply-styled/256/Spotify-icon.png");
        } catch (Exception e) {
            return null;
        }
    }


    public String getAppKey() {
        return "6086d2f27df04485a1e378bdb127646c";
    }


    @Override
    public URL getOAuthUrl() {
        try {
            return new URL("hhttps://accounts.spotify.com/authorize");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public URL getOAuthToken() {
        try {
            return new URL("https://accounts.spotify.com/api/token");
        } catch (Exception e) {
            return null;
        }
    }

    public String getAppSecret() {
        return "0d57beb5c2174c8aa2da8a46f2081b03";
    }

    @Override
    public TokenService updateToken(String code) {
        StringBuilder result = new StringBuilder();
        JSONObject resultJSON = new JSONObject();
        String parameters = "code=" + code +
                "&grant_type=authorization_code" +
                "&client_id=" + getAppKey() +
                "&client_secret=" + getAppSecret() +
                "&redirect_uri=http://localhost:4200/authentication/?service=" + this.getServiceName();
        URL url = getOAuthToken();
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

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
        return new TokenService(code, resultJSON.getString("access_token"), resultJSON.getString("refresh_token"));
    }

}
