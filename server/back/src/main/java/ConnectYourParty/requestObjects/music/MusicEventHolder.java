package ConnectYourParty.requestObjects.music;

public class MusicEventHolder {
    public String song;
    public String eventId;
    public String service;

    public MusicEventHolder(String song, String eventId, String service) {
        this.song = song;
        this.eventId = eventId;
        this.service = service;
    }
}
