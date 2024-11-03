package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Page<Policy> findByClientPesel(String pesel, Pageable pageable);
    Page<Policy> findByPolicyNumber(String policyNumber, Pageable pageable);
    Optional<Policy> findByPolicyNumber(String policyNumber);
    List<Policy> findAll();

}