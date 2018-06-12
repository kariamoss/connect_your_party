package ConnectYourParty.services.payment;

import ConnectYourParty.objects.TokenService;
import ConnectYourParty.services.IService;

import java.util.Optional;

public interface IPaymentService extends IService {
    void pay(String target, double amount, Optional<TokenService> token);
}
