package connectYourParty.auth;


import connectYourParty.businessObjects.Token;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TokenTest {
    private Token token;

    @Before
    public void init(){
        this.token = new Token();
    }

    @Test
    public void addInfoTest(){
        String value = "test";
        this.token.addAdditionalInfo(value,value);

        assertEquals(value,this.token.getInfo(value));
    }

    @Test
    public void noInfoTest(){
        assertNull(this.token.getInfo("test"));
    }
}
