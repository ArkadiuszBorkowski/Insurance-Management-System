package pl.borkowskiarkadiusz.insurancemanagementsystem.events;

/**
 * Class containing constant event messages used in the system.
 * These messages are used to update descriptive statuses on the claim form by the ClaimEventListener class.
 */
public class EventsMessages {
    public static final String CLAIM_AFTER_POLICY_EXPIRY_NO_RESERVE = "Zdarzenie ubezpieczeniowe miało miejsce po dacie wygaśnięcia polisy, ponadto rezerwa ubezpieczeniowa się wyczerpała. Roszczenie nie zostanie uznane.";
    public static final String CLAIM_AFTER_POLICY_EXPIRY = "Zdarzenie ubezpieczeniowe miało miejsce po dacie wygaśnięcia polisy, w związku z czym roszczenie nie może zostać uznane.";
    public static final String NEW_CLAIM_REGISTERED = "Rejestracja nowej szkody.";
    public static final String RESERVE_EXHAUSTED = "Rezerwa na polisie została wyczerpana. Roszczenie zostanie automatycznie zamknięte.";
    public static final String CLAIM_APPROVED_PENDING_PAYMENT = "Roszczenie zatwierdzone. Szkoda oczekuje na wypłatę płatności.";
    public static final String CLAIM_REJECTED = "Decyzja negatywna. Roszczenie odrzucone.";
    public static final String CLAIM_PAID = "Roszczenie zostało wypłacone.";
    public static final String CLAIM_PARTIALLY_PAID = "Roszczenie zostało częściowo wypłacone. Rezerwa ubezpieczeniowa została wyczerpana.";
}
