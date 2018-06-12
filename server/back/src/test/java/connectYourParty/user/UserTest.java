package connectYourParty.user;

import connectYourParty.businessObjects.Token;
import connectYourParty.businessObjects.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;

    @Before
    public void init(){
        user = new User("salut","salut");
    }


    @Test
    public void addTokenTest(){
        assertEquals(0,this.user.getTokenList().size());

        this.user.addToken(new Token("salut","sss","aaaa"));

        assertEquals(1,this.user.getTokenList().size());

    }

    @Test
    public void doubleTokenTest(){
        this.user.addToken(new Token("salut","Dropbox","aaaa"));
        assertEquals(1,this.user.getTokenList().size());

        this.user.addToken(new Token("ddd","Dropbox","qq"));
        assertEquals(1,this.user.getTokenList().size());
    }
}
