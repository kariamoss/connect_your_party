package ConnectYourParty.businessObjects;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    public User(){

    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
