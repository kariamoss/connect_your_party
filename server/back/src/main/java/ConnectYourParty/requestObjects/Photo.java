package ConnectYourParty.requestObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Photo {
    @XmlElement public String serviceName;
    @XmlElement public String format;
    @XmlElement public String name;
    @XmlElement public String photo;

    public boolean check(){
        return serviceName != null &&
                format != null &&
                name != null &&
                photo != null;
    }

    @Override
    public String toString(){
        return serviceName + " " + format + " " + name;
    }
}
