package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;

import java.time.LocalDate;
import java.util.Set;

/**
 * Data transfer object for policy information.
 */
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

    /**
     * Updates the policy status based on the current date and reserve amount.
     * Used in  @PostMapping("/policy") in PolicyController
     */

    public void updatePolicyStatus() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(startDate) && today.isBefore(endDate) && reserveAmount > 0) {
            this.policyStatus = PolicyStatus.AKTYWNA;
        } else if (today.isAfter(endDate) && reserveAmount > 0) {
            this.policyStatus = PolicyStatus.WYGASŁA;
        } else if (reserveAmount <= 0) {
            this.policyStatus = PolicyStatus.ZAMKNIĘTA;
        } else if (today.isEqual(startDate)) {
            this.policyStatus = PolicyStatus.NOWA;
        }
    }

    /**
     * Sets the start date and automatically sets the end date to one year later.
     *
     * @param startDate the start date of the policy
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.endDate = startDate.plusYears(1);
    }




}
