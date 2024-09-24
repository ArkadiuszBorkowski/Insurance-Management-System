package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClientDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.mapper.PolicyMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClientRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;

    @Autowired
    public PolicyService(PolicyRepository policyRepository, ModelMapper modelMapper, ClientRepository clientRepository) {
        this.policyRepository = policyRepository;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
    }

    public long getTotalPolicies() {
        return policyRepository.count();
    }

    public PolicyDTO getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid policy Id: " + id));
        return modelMapper.map(policy, PolicyDTO.class);
    }

    public Page<PolicyDTO> getPolicies(int page) {
        Page<Policy> policiesPage = policyRepository.findAll(PageRequest.of(page, 10));
        List<PolicyDTO> policyDTOs = policiesPage.stream()
                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(policyDTOs, PageRequest.of(page, 10), policiesPage.getTotalElements());
    }

    public PolicyDTO  savePolicy(PolicyDTO policyDTO) {
        System.out.println("Received PolicyDTO: " + policyDTO);
        Policy policy = modelMapper.map(policyDTO, Policy.class);
        System.out.println("Mapped data : " + policy);
        Policy savedPolicy = policyRepository.save(modelMapper.map(policy, Policy.class));
        return modelMapper.map(savedPolicy, PolicyDTO.class);
    }


}



