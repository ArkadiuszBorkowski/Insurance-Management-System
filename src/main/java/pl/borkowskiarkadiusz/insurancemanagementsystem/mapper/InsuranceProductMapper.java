package pl.borkowskiarkadiusz.insurancemanagementsystem.mapper;

import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;

import java.util.stream.Collectors;

public class InsuranceProductMapper {
    public static InsuranceProductDTO toDTO(InsuranceProduct product) {
        InsuranceProductDTO dto = new InsuranceProductDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setRisks(product.getRisks().stream().map(RiskMapper::toDTO).collect(Collectors.toSet()));
        return dto;
    }
}
