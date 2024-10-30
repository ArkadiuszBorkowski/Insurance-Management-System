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

@Service
public class PolicyStatusScheduler {

    @Autowired
    private PolicyRepository policyRepository;

    @PostConstruct // Uruchamia się w momencie uruchomienia aplikacji - do usunięcia po testach.
    public void init() {
        try {
            updatePolicyStatuses();
        } catch (Exception e) {
            e.printStackTrace(); // logowanie błędów
        }
    }


    //@Scheduled(fixedRate = 300000) // Uruchamianie co 5 minut - do testów
    @Scheduled(cron = "0 5 0 * * ?") // URUCHAMMIA SIĘ O 5 MIN PO PÓŁNOCY
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