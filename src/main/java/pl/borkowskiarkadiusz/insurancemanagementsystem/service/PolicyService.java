package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClientRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PolicyService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyService.class);
    private final PolicyRepository policyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PolicyService(PolicyRepository policyRepository, ModelMapper modelMapper) {
        this.policyRepository = policyRepository;
        this.modelMapper = modelMapper;
    }

    public long getTotalPolicies() {
        return policyRepository.count();
    }

    public PolicyDTO getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid policy Id: " + id));
        PolicyDTO policyDTO = modelMapper.map(policy, PolicyDTO.class);
        return policyDTO;

    }

    public Page<PolicyDTO> getPolicies(int page) {
        Page<Policy> policiesPage = policyRepository.findAll(PageRequest.of(page, 10));
        List<PolicyDTO> policyDTOs = policiesPage.stream()
                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(policyDTOs, PageRequest.of(page, 10), policiesPage.getTotalElements());
    }


    public Page<PolicyDTO> getPoliciesByPeselOrPolicyNumber(String pesel, String policyNumber, int page) {
        Page<Policy> policiesPage;
        if (pesel != null && !pesel.isEmpty()) {
            policiesPage = policyRepository.findByClientPesel(pesel, PageRequest.of(page, 10));
        } else if (policyNumber != null && !policyNumber.isEmpty()) {
            policiesPage = policyRepository.findByPolicyNumber(policyNumber, PageRequest.of(page, 10));
        } else {
            policiesPage = policyRepository.findAll(PageRequest.of(page, 10));
        }
        List<PolicyDTO> policyDTOs = policiesPage.stream()
                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(policyDTOs, PageRequest.of(page, 10), policiesPage.getTotalElements());
    }


    public PolicyDTO savePolicy(PolicyDTO policyDTO) {
        try {
            Policy policy = modelMapper.map(policyDTO, Policy.class);
            Policy savedPolicy = policyRepository.save(policy);
            return modelMapper.map(savedPolicy, PolicyDTO.class);
        } catch (Exception e) {
            logger.error("Error saving policy: {}", policyDTO, e);
            throw e;
        }
    }


}



