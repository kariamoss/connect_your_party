package ConnectYourParty.requestObjects.music;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MusicEventHolder {
    @XmlElement
    private String idSong;

    @XmlElement
    public String service;

    public String getIdSong() {
        return idSong;
    }

    public String getService() {
        return service;
    }
}
