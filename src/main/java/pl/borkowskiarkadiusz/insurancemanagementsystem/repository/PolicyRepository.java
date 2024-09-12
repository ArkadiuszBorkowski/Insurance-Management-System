package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Page<Policy> findAll(Pageable pageable);
    Page<Policy> findByClientPeselContaining(String pesel, Pageable pageable);
    Page<Policy> findByPolicyNumberContaining(String policyNumber, Pageable pageable);
}