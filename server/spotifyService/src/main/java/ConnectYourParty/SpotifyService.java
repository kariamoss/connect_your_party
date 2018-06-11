package ConnectYourParty;

import ConnectYourParty.exceptions.MissingTokenException;
import ConnectYourParty.exceptions.music.CannotCreatePlaylistException;
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
import java.util.logging.Logger;
import java.util.logging.Level;

import org.json.*;

public class SpotifyService implements IMusicService, IServiceOAuth {

    public final int searchResults = 10;
    private final String baseURL = "https://api.spotify.com/v1";


    public SpotifyService() {
    }


    @Override
    public MusicService getInfoFromId(String id, Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException {
        TokenService tokenService = getTokenService(token);
        return JsonToMusic(GET(baseURL + "/tracks/" + id, tokenService));
    }

    @Override
    public void addMusicFromId(String id, String playlist, Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException {
        TokenService tokenService = getTokenService(token);
        Optional<String> opt = this.getUserId(tokenService);

        if (!opt.isPresent()) {
            throw new GetMusicErrorException();
        }

        String path = this.baseURL + "/" +
                "users/" + opt.get() + "/" +
                "playlists/" + playlist + "/tracks?uris=spotify%3Atrack%3A" + id;
        try {
            this.POST(path, "", tokenService);
        } catch (Exception e) {
            throw new GetMusicErrorException("Cannot add music id " + id);
        }
    }

    @Override
    public PlaylistService addPlaylist(Optional<TokenService> token) throws MissingTokenException, CannotCreatePlaylistException {
        TokenService tokenService = getTokenService(token);
        JSONObject body = new JSONObject();

        body.put("name", "Coty");
        body.put("public", false);
        body.put("collaborative", true);

        try {
            String uri;
            Optional<String> opt = this.getUserId(tokenService);
            if (opt.isPresent()) {
                uri = this.baseURL + "/users/" + opt.get() + "/playlists";
            } else {
                throw new CannotGetUserId();
            }

            JSONObject response = new JSONObject(this.POST(uri, body.toString(), tokenService));

            return new PlaylistService(response.getString("id"));
        } catch (Exception e) {
            throw new CannotCreatePlaylistException("Cannot create spotify playlist");
        }
    }

    @Override
    public List<MusicService> getMusicFromPlaylist(String playlist, Optional<TokenService> token) throws MissingTokenException, GetMusicErrorException {
        TokenService tokenService = getTokenService(token);

        Optional<String> opt = this.getUserId(tokenService);

        if (!opt.isPresent()) {
            return new ArrayList<>();
        }


        String path = this.baseURL + "/" +
                "users/" + opt.get() +
                "/playlists/" + playlist + "/tracks";


        JSONObject jo = GET(path, tokenService);
        JSONArray arr = jo.getJSONArray("items");

        List<MusicService> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            list.add(JsonToMusic(arr.getJSONObject(i).getJSONObject("track")));
        }

        return list;

    }

    @Override
    public List<MusicService> searchMusic(String search, Optional<TokenService> token) throws GetMusicErrorException, MissingTokenException {
        TokenService tokenService = getTokenService(token);

        List<MusicService> list = new ArrayList<>();

        String query = null;
        try {
            query = String.format("q=%s&type=track&limit=" + searchResults,
                    URLEncoder.encode(search, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject jo = GET(baseURL + "/search?" + query, tokenService);
        JSONArray arr = jo.getJSONObject("tracks").getJSONArray("items");


        for (int i = 0; i < arr.length(); i++) {
            list.add(JsonToMusic(arr.getJSONObject(i)));
        }

        return list;
    }

    private TokenService getTokenService(Optional<TokenService> token) throws MissingTokenException {
        if(token.isPresent()){
            return token.get();
        }
        throw new MissingTokenException();
    }

    private Optional<String> getUserId(TokenService token) {
        try {
            JSONObject response = this.GET(this.baseURL + "/me", token);
            return Optional.of(response.getString("id"));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    private JSONObject GET(String uri, TokenService token) throws GetMusicErrorException {
        //We refresh the access token
        String accessToken = updateAccessToken(token.getRefreshToken());

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/json");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
            rd.close();
        } catch (Exception e) {
            throw new GetMusicErrorException("Error on request to Spotify Web Service : " + baseURL + uri + "\n" + Arrays.toString(e.getStackTrace()));
        }

        return new JSONObject(result.toString());
    }

    private String POST(String uri, String body, TokenService token) throws Exception {
        //We refresh the access token
        String accessToken = updateAccessToken(token.getRefreshToken());

        StringBuilder result = new StringBuilder();

        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.getOutputStream().write(body.getBytes("UTF8"));


        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

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
    public String getAppSecret() {
        return "0d57beb5c2174c8aa2da8a46f2081b03";
    }


    @Override
    public URL getOAuthUrl() {
        try {
            return new URL("hhttps://accounts.spotify.com/authorize");
        } catch (Exception e) {
            return null;
        }
    }


    private URL getOAuthToken() {
        try {
            return new URL("https://accounts.spotify.com/api/token");
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public TokenService generateToken(String code) {
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

    public String updateAccessToken(String refreshToken) throws GetMusicErrorException {

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String urlParameters = "client_id=" + getAppKey() + "&client_secret=" + getAppSecret()
                    + "&grant_type=refresh_token&refresh_token=" + refreshToken;

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
        return jo.getString("access_token");
    }

}
