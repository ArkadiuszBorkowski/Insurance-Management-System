package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;

import java.time.LocalDate;
import java.util.Optional;

public interface ClaimsRepository extends JpaRepository<Claims, Long> {
    Optional<Claims> findByPolicyId(Long policyId);
    Page<Claims> findByPolicyClientPeselOrClaimNumber(String pesel, String claimNumber, Pageable pageable);
    Page<Claims> findByPolicyClientPesel(String pesel, Pageable pageable);
    Page<Claims> findByClaimNumber(String claimNumber, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Claims c WHERE c.claimRegistrationDate = :today")
    long countClaimsRegisteredToday(@Param("today") LocalDate today);

    @Query("SELECT COUNT(c) FROM Claims c WHERE c.claimStatus = 'ZAMKNIĘTE'")
    long countClosedClaims();

    @Query("SELECT COUNT(c) FROM Claims c WHERE c.claimStatus = 'WYPŁACONE'")
    long countPaidClaims();
}
