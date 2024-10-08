package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import jakarta.persistence.Column;
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
    private PolicyDTO policy;
}