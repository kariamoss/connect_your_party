package ConnectYourParty;

import ConnectYourParty.exceptions.music.CannotGetUserId;
import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.objects.music.PlaylistService;
import ConnectYourParty.services.music.IMusicService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.*;

public class SpotifyService implements IMusicService {

    public final int searchResults = 10;
    private final String baseURL = "https://api.spotify.com/v1";

    private String token;
    private final String refresh_token = "AQAZsfm9j8Hc2EJX_gHAhjAA6sHiXr0e6_xn4HICjEylaNEN_Q_zNbNhMlhiTsQhwMh6uO0snLbRMH1mt6KiJlqL7q76BO_7bFhab3sYTjZtDIEhW_mx1t-53RSxMCuTF6g";

    public SpotifyService(){
    }


    public void updateToken() throws GetMusicErrorException {

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String urlParameters = "client_id="+getAppKey()+"&client_secret="+getAppSecret()+"&grant_type=refresh_token&refresh_token="+refresh_token;

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
        } catch(Exception e){
            throw new GetMusicErrorException("Error on request to Spotify Web Service : \n"+ Arrays.toString(e.getStackTrace()));
        }

        JSONObject jo = new JSONObject(result.toString());
        token = jo.getString("access_token");
    }

    @Override
    public MusicService getInfoFromId(String id) throws GetMusicErrorException {
        return JsonToMusic(GET(baseURL + "/tracks/" + id));
    }

    @Override
    public void addMusicFromId(String id, String playlist) throws GetMusicErrorException {
        Optional<String> opt = this.getUserId();

        if(!opt.isPresent()){
            throw new GetMusicErrorException();
        }

        String path = this.baseURL+ "/" +
                "users/" + opt.get() + "/"+
                "playlists/" + playlist + "/tracks?uris=spotify%3Atrack%3A" + id;
        try {
            this.POST(path, "");
        } catch (Exception e){
            throw new GetMusicErrorException();
        }
    }

    @Override
    public PlaylistService addPlaylist() {
        JSONObject body = new JSONObject();

        body.put("name","Coty");
        body.put("public",false);
        body.put("collaborative",true);

        try {
            String uri;
            Optional<String> opt = this.getUserId();
            if(opt.isPresent()){
                uri = this.baseURL +"/users/"+opt.get()+"/playlists";
            } else {
                throw new CannotGetUserId();
            }

            JSONObject response = new JSONObject(this.POST(uri, body.toString()));

            return new PlaylistService(response.getString("id"));
        } catch (Exception e){
            return new PlaylistService(null);
        }
    }

    @Override
    public List<MusicService> getMusicFromPlaylist(String playlist) {
        try {
            Optional<String> opt = this.getUserId();

            if (!opt.isPresent()) {
                return new ArrayList<>();
            }


            String path = this.baseURL + "/" +
                    "users/" + opt.get() +
                    "/playlists/" + playlist + "/tracks";


            JSONObject jo = GET(path);
            JSONArray arr = jo.getJSONArray("items");

            List<MusicService> list = new ArrayList();
            for (int i = 0; i < arr.length(); i++) {
                list.add(JsonToMusic(arr.getJSONObject(i).getJSONObject("track")));
            }

            return list;
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<MusicService> searchMusic(String search) throws GetMusicErrorException {

        List<MusicService> list = new ArrayList<>();

        String query = null;
        try {
            query = String.format("q=%s&type=track&limit="+searchResults,
                    URLEncoder.encode(search, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject jo = GET(baseURL + "/search?"+query);
        JSONArray arr = jo.getJSONObject("tracks").getJSONArray("items");


        for(int i =0;i<arr.length();i++){
            list.add(JsonToMusic(arr.getJSONObject(i)));
        }

        return list;
    }

    private Optional<String> getUserId(){
        try {
            JSONObject response = this.GET(this.baseURL + "/me");
            return Optional.of(response.getString("id"));
        } catch (Exception e){
            return Optional.empty();
        }
    }



    private JSONObject GET(String uri) throws GetMusicErrorException {
        updateToken();
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+ token);
            conn.setRequestProperty("Content-Type","application/json");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
            rd.close();
        } catch(Exception e){
            throw new GetMusicErrorException("Error on request to Spotify Web Service : "+ baseURL+uri+"\n"+ Arrays.toString(e.getStackTrace()));
        } //catch Exception token expiré(){}
        return new JSONObject(result.toString());
    }

    private String POST(String uri, String body) throws Exception{

        updateToken();
        StringBuilder result = new StringBuilder();

        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.getOutputStream().write(body.getBytes("UTF8"));


        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        return result.toString();
    }

    private MusicService JsonToMusic(JSONObject jo){

        String id = jo.getString("id");
        String title = jo.getString("name");
        String author = jo.getJSONArray("artists").getJSONObject(0).getString("name");
        return new MusicService(id,title,author);
    }

    @Override
    public String getServiceName() {
        return "Spotify";
    }

    @Override
    public URL getServiceIcon() {
        try {
            return new URL("http://icons.iconarchive.com/icons/dakirby309/simply-styled/256/Spotify-icon.png");
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public URL getOAuthUrl() {
        return null;
    }
    @Override
    public URL getOAuthToken() {
        return null;
    }

    @Override
    public String getAppKey() {
        return "6086d2f27df04485a1e378bdb127646c";
    }

    @Override
    public String getAppSecret() {
        return "0d57beb5c2174c8aa2da8a46f2081b03";
    }

}
