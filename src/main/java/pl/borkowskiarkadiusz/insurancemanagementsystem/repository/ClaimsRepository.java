package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;

import java.util.Optional;

public interface ClaimsRepository extends JpaRepository<Claims, Integer> {
    Optional<Claims> findByPolicyId(Long policyId);
}
