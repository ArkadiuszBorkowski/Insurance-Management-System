package pl.borkowskiarkadiusz.insurancemanagementsystem.events;

import org.springframework.context.ApplicationEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;

/**
 * Event class representing the update of a claim.
 * This class extends ApplicationEvent and includes the claim that was updated.
 * Events of this type are listened to by the ClaimEventListener class.
 */
public class ClaimUpdatedEvent extends ApplicationEvent {
    private final Claims claim;

    public ClaimUpdatedEvent(Object source, Claims claim) {
        super(source);
        this.claim = claim;
    }

    public Claims getClaim() {
        return claim;
    }
}