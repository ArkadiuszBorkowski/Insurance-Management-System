package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;

import java.time.LocalDate;

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

    //domyślne ustawienia dla nowej i "prawidłowej" szkody - event listener nadpisuje te zdarzenia przy walidacji.
    public void initializeDefaultValues() {
        this.claimRegistrationDate = LocalDate.now();
        this.decision = Decision.ANALIZA;
        this.claimStatus = ClaimStatus.NOWE_ROSZCZENIE;
        this.claimVerificationStatus = "Rejestracja nowej szkody.";
    }
}