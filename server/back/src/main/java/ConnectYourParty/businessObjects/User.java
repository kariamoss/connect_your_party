package ConnectYourParty.businessObjects;

import ConnectYourParty.objects.TokenService;
import ConnectYourParty.requestObjects.UserHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class User {

    @Id
    private String name;

    @NotNull
    private String surname;

    @OneToMany(
            cascade =
                    {
                            CascadeType.PERSIST,
                            CascadeType.MERGE
                    },
            fetch = FetchType.EAGER
    )
    private List<Token> tokenList;

    public User(){
        tokenList = new ArrayList<>();
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        tokenList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public Optional<TokenService> getToken(String serviceHost) {
        for (Token t : tokenList) {
            if (t.getServiceName().equals(serviceHost)) {
                return Optional.of(new TokenService(t.getCode(), t.getAccessToken(), t.getRefreshToken()));
            }
        }
        return Optional.empty();
    }

    public void addToken(Token token) {
        tokenList.add(token);
    }

    public UserHolder toHolder(){
        return new UserHolder(this.name,this.surname);
    }
}
