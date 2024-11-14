package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Data Transfer Object (DTO) representing a risk.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RiskDTO {
    private Long id;
    private String riskName;
    private String iconName;
}