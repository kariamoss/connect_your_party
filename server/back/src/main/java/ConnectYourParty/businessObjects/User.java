package ConnectYourParty.businessObjects;

import ConnectYourParty.businessObjects.service.UserStatus;
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

    private UserStatus status;

    public User(){
        tokenList = new ArrayList<>();
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.status = UserStatus.DEFAULT;
        this.tokenList = new ArrayList<>();
    }

    public User(String name, String surname, UserStatus status) {
        this.name = name;
        this.surname = surname;
        this.status = status;
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

    public Optional<Token> getToken(String serviceHost) {
        for (Token t : tokenList) {
            if (t.getServiceName().equals(serviceHost)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public void addToken(Token token) {
        for(Token tok : tokenList){
            if(tok.getServiceName().equals(tok.getServiceName())){
                tokenList.remove(tok);
                break;
            }
        }
        tokenList.add(token);
    }

    public UserHolder toHolder(){
        return new UserHolder(this.name,this.surname,this.status.getStatus());
    }
}
