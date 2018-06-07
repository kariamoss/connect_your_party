package ConnectYourParty;

import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
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
    private String token = "BQBAoZWvlFZgiM-UiuQzHCNoYW95pEnvQeEOvJzk7toyLa7H3rOblblvIpWuKcKz01XBxwg309AmdDTAVHJV92G3aAxDlRo3UwnD_A9-xJERcypaKqFwpO5b0hSKfMiph8btlzl3ZZG5_823MMlvL9HR6xUD-2T6Q3n0kFlsrnlkpHwiFT_eDcUx7v4B8CBlcCXbGG7Pdpi8qbthl8yL6W8EGHL4FtP82wPz_XeDTNXifwwqxzZiiMGXnZlQotMIwhA-iWftlkY";

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