package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InsuranceProductDTO {
    private Long id;
    private String productName;
    private String description;
    private Set<RiskDTO> risks;
}
