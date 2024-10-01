package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Page<Policy> findByClientPesel(String pesel, Pageable pageable);
    Page<Policy> findByPolicyNumber(String policyNumber, Pageable pageable);
}