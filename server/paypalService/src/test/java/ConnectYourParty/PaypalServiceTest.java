package ConnectYourParty;

import connectYourParty.PaypalService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PaypalServiceTest {
    private static PaypalService service;

    @BeforeClass
    public static void init(){
        service = new PaypalService();
    }

    @Test
    public void UrlTest(){
        assertNotNull(service.getServiceIcon());
    }

    @Test
    public void OAuthTest(){
        assertNotNull(service.getOAuthUrl());
    }
}
