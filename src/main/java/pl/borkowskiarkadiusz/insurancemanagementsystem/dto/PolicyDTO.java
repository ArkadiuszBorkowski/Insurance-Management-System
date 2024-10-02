package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PolicyDTO {
    private Long id;
    private String policyNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double premium;
    private Double coverageAmount;
    private Double reserveAmount;
    private ClientDTO client;
    private InsuranceProductDTO insuranceProduct;
    private List<ClaimsDTO> claims; // Dodane pole


}
