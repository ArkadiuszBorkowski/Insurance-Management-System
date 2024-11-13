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
import pl.borkowskiarkadiusz.insurancemanagementsystem.events.ClaimUpdatedEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.events.EventsMessages;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

@Component
public class ClaimEventListener {

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ClaimsRepository claimsRepository;

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
        if (claim.getClaimDate().isAfter(policy.getEndDate()) || claim.getClaimDate().isBefore(policy.getStartDate())) {
            policy.setPolicyStatus(PolicyStatus.WYGASŁA);
            claim.setClaimStatus(ClaimStatus.ODRZUCONE);
            claim.setDecision(Decision.ODMOWA);

            if (policy.getReserveAmount() == 0) {
                claim.setClaimVerificationStatus(EventsMessages.CLAIM_AFTER_POLICY_EXPIRY_NO_RESERVE);
            } else {
                claim.setClaimVerificationStatus(EventsMessages.CLAIM_AFTER_POLICY_EXPIRY);
            }
        }
    }

    private void handleValidPolicy(Claims claim, Policy policy) {
        if (claim.getClaimDate().isBefore(policy.getEndDate()) && claim.getClaimDate().isAfter(policy.getStartDate())) {
            if (policy.getReserveAmount() > 0) {
                claim.setClaimStatus(ClaimStatus.NOWE_ROSZCZENIE);
                claim.setDecision(Decision.ANALIZA);
                claim.setClaimVerificationStatus(EventsMessages.NEW_CLAIM_REGISTERED);
            } else {
                claim.setClaimStatus(ClaimStatus.ZAMKNIĘTE);
                claim.setDecision(Decision.ODMOWA);
                claim.setClaimVerificationStatus(EventsMessages.RESERVE_EXHAUSTED);
            }
        }
    }


    @EventListener
    public void handleClaimPaidEvent(ClaimPaidEvent event) {
        Claims claim = event.getClaim();
        Policy policy = policyRepository.findById(claim.getPolicy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        double paymentAmount = event.getPaymentAmount();
        double reserveAmount = policy.getReserveAmount();

        if (paymentAmount >= reserveAmount) {
            paymentAmount = reserveAmount;
            policy.setReserveAmount(0.0);
            claim.setClaimVerificationStatus(EventsMessages.CLAIM_PARTIALLY_PAID);
            policy.setPolicyStatus(PolicyStatus.WYGASŁA);
        } else {
            policy.setReserveAmount(reserveAmount - paymentAmount);
            claim.setClaimVerificationStatus(EventsMessages.CLAIM_PAID);
        }
        claim.setClaimStatus(ClaimStatus.WYPŁACONE);

        claimsRepository.save(claim);
        policyRepository.save(policy);
    }


    @EventListener
    public void handleClaimUpdatedEvent(ClaimUpdatedEvent event) {
        Claims claim = event.getClaim();
        Policy policy = policyRepository.findById(claim.getPolicy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        if (claim.getDecision().equals(Decision.AKCEPTACJA) && claim.getPaymentAmount() == null) {
            claim.setClaimStatus(ClaimStatus.OCZEKIWANIE_NA_PŁATNOŚĆ);
            claim.setClaimVerificationStatus(EventsMessages.CLAIM_APPROVED_PENDING_PAYMENT);
        } else if (claim.getDecision().equals(Decision.ODMOWA)) {
            if (!policy.getPolicyStatus().equals(PolicyStatus.WYGASŁA)) {
                claim.setClaimStatus(ClaimStatus.ODRZUCONE);
                claim.setClaimVerificationStatus(EventsMessages.CLAIM_REJECTED);
            }
        }


        claimsRepository.save(claim);
      }
}