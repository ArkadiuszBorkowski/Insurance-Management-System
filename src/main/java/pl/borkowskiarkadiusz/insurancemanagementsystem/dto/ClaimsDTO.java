package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;

import java.time.LocalDate;

/**
 * Data transfer object for entity Claims .
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClaimsDTO {
    private Long id;
    private String claimNumber;
    private String description;
    private LocalDate claimDate;
    private LocalDate claimRegistrationDate;
    private ClaimStatus claimStatus;
    private Decision decision;
    private Double paymentAmount;
    private LocalDate paymentDate;
    private PolicyDTOWithoutClaims policy;
    private String claimVerificationStatus;

    /**
     * Initializes default values for a new and "valid" claim.
     * Event listeners override these values during validation.
     */
    public void initializeDefaultValues() {
        this.claimRegistrationDate = LocalDate.now();
        this.decision = Decision.ANALIZA;
        this.claimStatus = ClaimStatus.NOWE_ROSZCZENIE;
        this.claimVerificationStatus = "Rejestracja nowej szkody.";
    }
}