package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PolicyDTO extends PolicyDTOWithoutClaims {
    private Long id;
    private String policyNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double premium;
    private Double coverageAmount;
    private Double reserveAmount;
    private PolicyStatus policyStatus;
    private ClientDTO client;
    private InsuranceProductDTO insuranceProduct;
    private Set<ClaimsDTO> claims; // Dodane pole

    public void updatePolicyStatus() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(startDate) && today.isBefore(endDate) && reserveAmount > 0) {
            this.policyStatus = PolicyStatus.AKTYWNA;
        } else if (today.isAfter(endDate) && reserveAmount > 0) {
            this.policyStatus = PolicyStatus.WYGASŁA;
        } else if (reserveAmount <= 0) {
            this.policyStatus = PolicyStatus.ZAMKNIĘTA;
        }
    }

}
