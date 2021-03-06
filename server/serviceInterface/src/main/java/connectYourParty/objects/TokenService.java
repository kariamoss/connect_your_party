package connectYourParty.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * A business object used to send information about the tokens in a oAuth2.0 authentication
 * Saved on COTY-side to ensure that every token is linked with the correct user
 */
public class TokenService {
    private String code;
    private String accessToken;
    private String refreshToken;
    private Map<String, String> additionalInfos;

    /**
     *
     * @param code Code generated in the get request client-side. Used to generate {@link TokenService#accessToken}
     *             and {@link TokenService#refreshToken}
     * @param accessToken Token used in every request for the service
     * @param refreshToken Token used to get a new valid {@link TokenService#accessToken}
     */
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
