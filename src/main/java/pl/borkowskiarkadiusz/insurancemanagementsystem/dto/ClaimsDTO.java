package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ClaimsDTO {
    private Long id;
    private String description;
    private LocalDate claimDate;
    private ClaimStatus claimStatus;
    private Decision decision;
    private PolicyDTO policy;
}