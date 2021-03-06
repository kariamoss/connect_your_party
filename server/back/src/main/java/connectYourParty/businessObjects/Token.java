package connectYourParty.businessObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(length = 15000)
    private String code;

    @NotNull
    private String serviceName;

    @NotNull
    @Column(length = 15000)
    private String accessToken;

    @Column(length = 15000)
    private String refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String,String> additionalInfo;

    public Token() {
        this.additionalInfo = new HashMap<>();
    }

    public Token(String code, String serviceName, String accessToken) {
        this.code = code;
        this.serviceName = serviceName;
        this.accessToken = accessToken;
        this.additionalInfo = new HashMap<>();
    }

    public String getCode() {
        return code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(code, token.code) &&
                Objects.equals(serviceName, token.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, serviceName);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void addAdditionalInfo(String key, String value){
        this.additionalInfo.put(key,value);
    }

    public String getInfo(String key){
        return this.additionalInfo.get(key);
    }
}
