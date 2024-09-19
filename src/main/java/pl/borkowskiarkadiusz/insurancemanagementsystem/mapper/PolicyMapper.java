package pl.borkowskiarkadiusz.insurancemanagementsystem.mapper;

import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;

public class PolicyMapper {
    public static PolicyDTO toDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setId(policy.getId());
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setProductName(policy.getInsuranceProduct().getProductName());
        dto.setPremium(policy.getPremium());
        dto.setClientFirstName(policy.getClient().getFirstName());
        dto.setClientLastName(policy.getClient().getLastName());
        dto.setClientPesel(policy.getClient().getPesel());
        return dto;
    }
}