package connectYourParty.payment;

import connectYourParty.objects.TokenService;
import connectYourParty.services.payment.IPaymentService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestPaymentService implements IPaymentService {
    public static String urlExec = "http://execute.com";
    public static String urlToReturn = "http://salut.com";
    public static String serviceName = "Paypal";

    @Override
    public List<URL> buildPayment(String target, double amount, Optional<TokenService> token) {

        try {
            return Arrays.asList(new URL(urlExec),
                    new URL(urlToReturn));
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public void confirm(String payerId, Optional<TokenService> token) {

    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public URL getServiceIcon() {
        return null;
    }
}
