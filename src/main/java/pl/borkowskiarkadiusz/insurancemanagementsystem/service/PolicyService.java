package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public long getTotalPolicies() {
        return policyRepository.count();
    }

}