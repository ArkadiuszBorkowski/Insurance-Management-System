package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class PolicyDTOWithoutClaims {
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
}