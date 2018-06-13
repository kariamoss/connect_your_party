package connectYourParty.objects;

import java.util.HashMap;
import java.util.Map;

public class TokenService {
    private String code;
    private String accessToken;
    private String refreshToken;
    private Map<String, String> additionalInfos;

    public TokenService(String code, String accessToken, String refreshToken) {
        this.code = code;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.additionalInfos = new HashMap<>();
    }

    public TokenService(String code, String accessToken, String refreshToken, Map<String, String> additionalInfos) {
        this.code = code;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.additionalInfos = additionalInfos;
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

    public Map<String, String> getAdditionalInfos() {
        return additionalInfos;
    }
}
