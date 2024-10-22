package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.generator.ClaimsNumberGenerator;

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
    private PolicyDTOWithoutClaims policy;


    public void initializeDefaultValues() {
        this.claimRegistrationDate = LocalDate.now();
        this.claimStatus = ClaimStatus.NOWE_ROSZCZENIE;
        this.decision = Decision.ANALIZA;
        //this.claimNumber = ClaimsNumberGenerator.generateClaimsNumber();
    }
}