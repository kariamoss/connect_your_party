package ConnectYourParty;

import ConnectYourParty.exceptions.music.GetMusicErrorException;
import ConnectYourParty.objects.music.MusicService;
import ConnectYourParty.services.music.IMusicService;

import java.io.BufferedReader;
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
    private final String token = "BQCki3TilX8c74StbrVs1iqz5NkkITFL1nHKNbMJwXebS0QTtO2WI8s20wBW82nqFeg_Kx89d48dmxm59vfGs_c9Lowp9sZAAkds9yrYUrqqF7t8HL7UW5sMy6fXpGAMlJx7NOpt6rQR4xyltAVc0z3e-q5MlSjejNer4ww1TPLFsRIMx_qjaoRabqa4_ltijFlII0ivpMVsfsVyozAQOcFXadG-LD4AnKkeNeI69EUzmzMdIWD83C8aVJcOSXCPc0Tb1y4C";

    public SpotifyService(){}

    @Override
    public MusicService getInfoFromId(String id) throws GetMusicErrorException {
        return JsonToMusic(GET("/tracks/" + id));
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

        JSONObject jo = GET("/search?"+query);
        JSONArray arr = jo.getJSONObject("tracks").getJSONArray("items");


        for(int i =0;i<arr.length();i++){
            list.add(JsonToMusic(arr.getJSONObject(i)));
        }

        return list;
    }



    private JSONObject GET(String uri) throws GetMusicErrorException {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(baseURL + uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+ token);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
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
    public URL getOAuth() {
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
