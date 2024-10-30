package pl.borkowskiarkadiusz.insurancemanagementsystem.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.events.ClaimCreatedEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.events.ClaimPaidEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

@Component
public class PolicyEventListener {

    @Autowired
    private PolicyRepository policyRepository;

    @EventListener
    public void handleClaimCreatedEvent(ClaimCreatedEvent event) {
        Claims claim = event.getClaim();
        Policy policy = policyRepository.findById(claim.getPolicy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        handleExpiredPolicy(claim, policy);
        handleValidPolicy(claim, policy);

        policyRepository.save(policy);
    }

    private void handleExpiredPolicy(Claims claim, Policy policy) {
        if (claim.getClaimDate().isAfter(policy.getEndDate())) {
            policy.setPolicyStatus(PolicyStatus.WYGASŁA);
            claim.setClaimStatus(ClaimStatus.ODRZUCONE);
            claim.setDecision(Decision.ODMOWA);

            if (policy.getReserveAmount() == 0) {
                claim.setClaimVerificationStatus("Zdarzenie ubezpieczeniowe miało miejsce po dacie wygaśnięcia polisy, " +
                        "ponadto rezerwa ubezpieczeniowa się wyczerpała. Roszczenie nie zostanie uznane.");
            } else {
                claim.setClaimVerificationStatus("Zdarzenie ubezpieczeniowe miało miejsce po dacie wygaśnięcia polisy, " +
                        "w związku z czym roszczenie nie może zostać uznane.");
            }
        }
    }

    private void handleValidPolicy(Claims claim, Policy policy) {
        if (claim.getClaimDate().isBefore(policy.getEndDate()) && claim.getClaimDate().isAfter(policy.getStartDate())) {
            if (policy.getReserveAmount() == 0) {
                claim.setClaimStatus(ClaimStatus.ZAMKNIĘTE);
                claim.setDecision(Decision.ODMOWA);
                claim.setClaimVerificationStatus("Rezerwa na polisie została wyczerpana. Roszczenie zostanie automatycznie zamknięte.");
            }
        }
    }



    @EventListener
    public void handleClaimPaidEvent(ClaimPaidEvent event) {
        Claims claim = event.getClaim();
        Policy policy = policyRepository.findById(claim.getPolicy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        double paymentAmount = event.getPaymentAmount();

        policy.setReserveAmount(policy.getReserveAmount() - paymentAmount);
        policy.setPolicyStatus(PolicyStatus.WYGASŁA);

        policyRepository.save(policy);
    }
}