package connectYourParty.objects.music;

/**
 * A business object used to send information asked about a playlist in the service
 */
public class PlaylistService {
    private String id;

    /**
     *
     * @param id The playlist id that is stored in the service
     */
    public PlaylistService(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
