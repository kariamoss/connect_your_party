package ConnectYourParty;

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
import org.json.*;

public class SpotifyService implements IMusicService {

    public final int searchResults = 10;
    private final String baseURL = "https://api.spotify.com/v1";
    private String token = "BQB_ztZ93VNEUMWSY6f7mKkajKv_o19WCUVDqRrBM144p2iEzG6zmQtSHh79WvZiZJt52nQWOSUtYJyQSPwRVLWwLV_b0KH7JY0NMHYKxZr5u4aGaSffiWSod4y5EKUhnwonQo4rodH29KOAcx5frgTHgi611uVbMqG6Giwnas6uLTfKMS0zbVi-ZRxDiiD3m7e36T5Y3qQIvQm2C0Y5xtYRI36GV_ikupgb6rvCF-XYSfvC9p4sLvt5x9BtQ-Qe3R24WPFsVyU";
    public SpotifyService(){
    }


    public void updateToken() throws GetMusicErrorException {

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String urlParameters = "client_id="+getAppKey()+"&client_secret="+getAppSecret()+"&grant_type=client_credentials";

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
        System.out.println(jo.getString("access_token"));
    }

    @Override
    public MusicService getInfoFromId(String id) throws GetMusicErrorException {
        return JsonToMusic(GET(baseURL + "/tracks/" + id));
    }

    @Override
    public void addMusicFromId(String id, String playlist) throws GetMusicErrorException {
        //TODO Add to playlist if exist
    }

    @Override
    public PlaylistService addPlaylist() {
        return null;
    }

    @Override
    public List<MusicService> getMusicFromPlaylist(String playlist) {
        return null;
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



    private JSONObject GET(String uri) throws GetMusicErrorException {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+ token);
            System.out.printf("Status code : "+conn.getResponseCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
            System.out.println(result.toString());
            rd.close();
        } catch(Exception e){
            throw new GetMusicErrorException("Error on request to Spotify Web Service : "+ baseURL+uri+"\n"+ Arrays.toString(e.getStackTrace()));
        }
        return new JSONObject(result.toString());
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
