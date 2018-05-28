package ConnectYourParty.requestObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Photo {
    @XmlElement public String hello;
}
