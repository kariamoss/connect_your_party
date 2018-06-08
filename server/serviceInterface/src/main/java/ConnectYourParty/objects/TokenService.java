package ConnectYourParty.objects;

import java.util.Optional;

public class TokenService {
    private String code;
    private String accessToken;
    private String refreshToken;

    public TokenService(String code, String accessToken, String refreshToken) {
        this.code = code;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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
}
