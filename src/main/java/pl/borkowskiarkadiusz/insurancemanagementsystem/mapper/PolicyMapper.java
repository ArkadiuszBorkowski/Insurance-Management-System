package pl.borkowskiarkadiusz.insurancemanagementsystem.mapper;

import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;

import java.util.Set;
import java.util.stream.Collectors;

public class PolicyMapper {
    public static PolicyDTO toDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setId(policy.getId());
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setProductName(policy.getInsuranceProduct().getProductName());
        dto.setPremium(policy.getPremium());
        dto.setCoverageAmount(policy.getCoverageAmount());
        dto.setReserveAmount(policy.getReserveAmount());

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName(policy.getClient().getFirstName());
        clientDTO.setLastName(policy.getClient().getLastName());
        clientDTO.setPesel(policy.getClient().getPesel());
        clientDTO.setDateOfBirth(policy.getClient().getDateOfBirth());
        clientDTO.setEmail(policy.getClient().getEmail());
        clientDTO.setMobileNumber(policy.getClient().getMobileNumber());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(policy.getClient().getAddress().getStreet());
        addressDTO.setStreetNo(policy.getClient().getAddress().getStreetNo());
        addressDTO.setApartmentNo(policy.getClient().getAddress().getApartmentNo());
        addressDTO.setCity(policy.getClient().getAddress().getCity());
        addressDTO.setZipcode(policy.getClient().getAddress().getZipcode());
        clientDTO.setAddress(addressDTO);

        dto.setClient(clientDTO);

        InsuranceProductDTO productDTO = new InsuranceProductDTO();
        productDTO.setId(policy.getInsuranceProduct().getId());
        productDTO.setProductName(policy.getInsuranceProduct().getProductName());
        productDTO.setDescription(policy.getInsuranceProduct().getDescription());

        Set<RiskDTO> riskDTOs = policy.getInsuranceProduct().getRisks().stream()
                .map(risk -> {
                    RiskDTO riskDTO = new RiskDTO();
                    riskDTO.setId(risk.getId());
                    riskDTO.setRiskName(risk.getRiskName());
                    riskDTO.setIconName(risk.getIconName());
                    return riskDTO;
                }).collect(Collectors.toSet());
        productDTO.setRisks(riskDTOs);

        dto.setInsuranceProduct(productDTO);

        return dto;
    }
}