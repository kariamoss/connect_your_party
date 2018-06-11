package ConnectYourParty.requestObjects.music;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MusicEventHolder {
    public String idSong;

    public String service;

    public MusicEventHolder(){}

    public MusicEventHolder(String idSong, String service) {
        this.idSong = idSong;
        this.service = service;
    }

    public String getIdSong() {
        return idSong;
    }

    public String getService() {
        return service;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public void setService(String service) {
        this.service = service;
    }
}
