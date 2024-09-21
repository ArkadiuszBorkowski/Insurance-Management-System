package pl.borkowskiarkadiusz.insurancemanagementsystem.mapper;

import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Risk;

public class RiskMapper {
    public static RiskDTO toDTO(Risk risk) {
        RiskDTO dto = new RiskDTO();
        dto.setId(risk.getId());
        dto.setRiskName(risk.getRiskName());
        dto.setIconName(risk.getIconName());
        return dto;
    }

    public static Risk toEntity(RiskDTO dto) {
        Risk risk = new Risk();
        risk.setId(dto.getId());
        risk.setRiskName(dto.getRiskName());
        risk.setIconName(dto.getIconName());
        return risk;
    }
}
