package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class responsible for scheduling and updating policy statuses.
 * This class updates the statuses of policies based on their start and end dates, and the reserve amount.
 */

@Service
public class PolicyStatusScheduler {


    private PolicyRepository policyRepository;

    public PolicyStatusScheduler(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    /**
     * Initializes the scheduler and updates policy statuses.
     * This method is executed after the bean's initialization.
     * It is used for testing purposes and should be removed after testing.
     */
    @PostConstruct
    public void init() {
        try {
            updatePolicyStatuses();
        } catch (Exception e) {
            e.printStackTrace(); // logowanie błędów
        }
    }


    /**
     * Updates the statuses of all policies based on their dates and reserve amounts.
     * This method is scheduled to run at 5 minutes past midnight every day.
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void updatePolicyStatuses() {
        List<Policy> policies = policyRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Policy policy : policies) {
            boolean statusChanged = false;

            if (today.isAfter(policy.getEndDate()) && policy.getReserveAmount() > 0) {
                policy.setPolicyStatus(PolicyStatus.WYGASŁA);
                statusChanged = true;
            } else if (policy.getReserveAmount() <= 0) {
                policy.setPolicyStatus(PolicyStatus.ZAMKNIĘTA);
                statusChanged = true;
            } else if (today.isAfter(policy.getStartDate()) && today.isBefore(policy.getEndDate()) && policy.getReserveAmount() > 0) {
                policy.setPolicyStatus(PolicyStatus.AKTYWNA);
                statusChanged = true;
            }

            if (statusChanged) {
                policyRepository.save(policy);
            }
        }
    }
}