package pl.borkowskiarkadiusz.insurancemanagementsystem.mapper;

import org.springframework.stereotype.Component;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Address;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PolicyMapper {

    public  PolicyDTO toDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setId(policy.getId());
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
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

    public  Policy toEntity(PolicyDTO policyDTO) {
        if (policyDTO == null) {
            return null;
        }

        Policy policy = new Policy();
        policy.setId(policyDTO.getId());
        policy.setPolicyNumber(policyDTO.getPolicyNumber());
        policy.setStartDate(policyDTO.getStartDate());
        policy.setEndDate(policyDTO.getEndDate());
        policy.setPremium(policyDTO.getPremium());
        policy.setCoverageAmount(policyDTO.getCoverageAmount());
        policy.setReserveAmount(policyDTO.getReserveAmount());

        if (policyDTO.getClient() != null) {
            Client client = new Client();
            client.setFirstName(policyDTO.getClient().getFirstName());
            client.setLastName(policyDTO.getClient().getLastName());
            client.setPesel(policyDTO.getClient().getPesel());
            client.setDateOfBirth(policyDTO.getClient().getDateOfBirth());
            client.setEmail(policyDTO.getClient().getEmail());
            client.setMobileNumber(policyDTO.getClient().getMobileNumber());

            Address address = new Address();
            address.setStreet(policyDTO.getClient().getAddress().getStreet());
            address.setStreetNo(policyDTO.getClient().getAddress().getStreetNo());
            address.setApartmentNo(policyDTO.getClient().getAddress().getApartmentNo());
            address.setCity(policyDTO.getClient().getAddress().getCity());
            address.setZipcode(policyDTO.getClient().getAddress().getZipcode());

            client.setAddress(address);
            policy.setClient(client);
        }

        if (policyDTO.getInsuranceProduct() != null) {
            InsuranceProduct insuranceProduct = new InsuranceProduct();
            insuranceProduct.setId(policyDTO.getInsuranceProduct().getId());
            insuranceProduct.setProductName(policyDTO.getInsuranceProduct().getProductName());
            insuranceProduct.setDescription(policyDTO.getInsuranceProduct().getDescription());
            policy.setInsuranceProduct(insuranceProduct);
        }

        return policy;
    }
}