package connectYourParty.objects.music;

public class MusicService {
    private String id;

    private String title;

    private String artist;

    public MusicService(String id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String toString(){
        return "\"" + title + "\" de " + artist + " (id="+id+")";
    }
}