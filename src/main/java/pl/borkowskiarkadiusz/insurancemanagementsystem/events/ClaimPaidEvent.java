package pl.borkowskiarkadiusz.insurancemanagementsystem.events;

import org.springframework.context.ApplicationEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;

/**
 * Event class representing the payment of a claim.
 * This class extends ApplicationEvent and includes the claim that was paid and the payment amount.
 * Events of this type are listened to by the ClaimEventListener class.
 */
public class ClaimPaidEvent extends ApplicationEvent {
    private final Claims claim;
    private final double paymentAmount;

    public ClaimPaidEvent(Object source, Claims claim, double paymentAmount) {
        super(source);
        this.claim = claim;
        this.paymentAmount = paymentAmount;
    }

    public Claims getClaim() {
        return claim;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
}