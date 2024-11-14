package pl.borkowskiarkadiusz.insurancemanagementsystem.events;

import org.springframework.context.ApplicationEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;

/**
 * Event class representing the creation of a claim.
 * This class extends ApplicationEvent and includes the claim that was created.
 * Events of this type are listened to by the ClaimEventListener class.
 */
public class ClaimCreatedEvent extends ApplicationEvent {
    private final Claims claim;

    public ClaimCreatedEvent(Object source, Claims claim) {
        super(source);
        this.claim = claim;
    }

    public Claims getClaim() {
        return claim;
    }
}

