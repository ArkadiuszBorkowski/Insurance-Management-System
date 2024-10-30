package pl.borkowskiarkadiusz.insurancemanagementsystem.events;

import org.springframework.context.ApplicationEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;

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

