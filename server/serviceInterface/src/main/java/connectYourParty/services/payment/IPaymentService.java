package connectYourParty.services.payment;



import connectYourParty.objects.TokenService;
import connectYourParty.services.IService;

import java.net.URL;
import java.util.Optional;

public interface IPaymentService extends IService {
    URL buildPayment(String target, double amount, Optional<TokenService> token);

    void confirm(String payerId, Optional<TokenService> token);


}
