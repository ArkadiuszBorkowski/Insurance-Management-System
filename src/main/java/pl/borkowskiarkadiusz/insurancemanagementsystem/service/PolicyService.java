package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTOWithoutClaims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PolicyService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyService.class);

    private final PolicyRepository policyRepository;
    private final ModelMapper modelMapper;
    private final SpringTemplateEngine templateEngine;


    @Autowired
    public PolicyService(PolicyRepository policyRepository, ModelMapper modelMapper, SpringTemplateEngine templateEngine) {
        this.policyRepository = policyRepository;
        this.modelMapper = modelMapper;
        this.templateEngine = templateEngine;
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


 /*   public Page<PolicyDTO> getPoliciesByPeselOrPolicyNumber(String pesel, String policyNumber, int page) {
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
    }*/

    public Page<PolicyDTO> getPoliciesByPeselOrPolicyNumber(String pesel, String policyNumber, String sortBy, int page) {
        Page<Policy> policiesPage;
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy));

        if (pesel != null && !pesel.isEmpty()) {
            policiesPage = policyRepository.findByClientPesel(pesel, pageable);
        } else if (policyNumber != null && !policyNumber.isEmpty()) {
            policiesPage = policyRepository.findByPolicyNumber(policyNumber, pageable);
        } else {
            policiesPage = policyRepository.findAll(pageable);
        }

        List<PolicyDTO> policyDTOs = policiesPage.stream()
                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(policyDTOs, pageable, policiesPage.getTotalElements());
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

    public List<String> getTemplateNames() {
        File folder = new File("src/main/resources/templates/policy_documents/");
        File[] listOfFiles = folder.listFiles();
        return Arrays.stream(listOfFiles)
                .filter(file -> file.isFile() && file.getName().endsWith(".html"))
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public String getHtmlContent(String templateName, PolicyDTO policyDTO) {
        Context context = new Context();
        context.setVariable("policy", policyDTO);
        return templateEngine.process("policy_documents/" + templateName, context);
    }


    public Optional<PolicyDTOWithoutClaims> checkPolicyNumber(String policyNumber) {
        return policyRepository.findByPolicyNumber(policyNumber)
                .map(policy -> modelMapper.map(policy, PolicyDTOWithoutClaims.class));
    }


}



