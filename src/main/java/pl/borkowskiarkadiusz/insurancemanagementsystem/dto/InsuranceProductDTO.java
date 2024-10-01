package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class InsuranceProductDTO {
    private Long id;
    private String productName;
    private String description;
    private Set<RiskDTO> risks;
}
