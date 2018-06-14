package connectYourParty.objects.music;

/**
 * A business object used to send information asked about a music in the service
 */
public class MusicService {
    private String id;

    private String title;

    private String artist;

    /**
     *
     * @param id The music id that is stored in the service
     * @param title
     * @param artist
     */
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
