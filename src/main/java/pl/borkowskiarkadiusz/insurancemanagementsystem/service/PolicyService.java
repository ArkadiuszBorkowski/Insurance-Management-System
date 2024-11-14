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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing policies.
 * This class provides methods to create, update, retrieve, and list policies, as well as to handle policy-related operations.
 */
@Service
public class PolicyService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyService.class);

    private final PolicyRepository policyRepository;
    private final ModelMapper modelMapper;
    private final SpringTemplateEngine templateEngine;


    /**
     * Constructs a new PolicyService with the specified dependencies.
     *
     * @param policyRepository the repository for managing policies
     * @param modelMapper the model mapper for converting between entities and DTOs
     * @param templateEngine the template engine for processing HTML templates
     */
    public PolicyService(PolicyRepository policyRepository, ModelMapper modelMapper, SpringTemplateEngine templateEngine) {
        this.policyRepository = policyRepository;
        this.modelMapper = modelMapper;
        this.templateEngine = templateEngine;
    }

    /**
     * Retrieves a policy by its ID.
     *
     * @param id the ID of the policy to retrieve
     * @return the PolicyDTO of the found policy
     * @throws ResourceNotFoundException if the policy is not found
     */
    public PolicyDTO getPolicyById(Long id) {
        return policyRepository.findById(id)
                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Brak polisy o numerze id: " + id));
    }

    /**
     * Retrieves policies by client's PESEL or policy number with pagination and sorting.
     * @param pesel the PESEL of the client
     * @param policyNumber the policy number
     * @param sortBy the field to sort by
     * @param page the page number to retrieve
     * @return a Page of PolicyDTOs
     */
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

    /**
     * Saves a new policy.
     * @param policyDTO the DTO of the policy to save
     * @return the saved PolicyDTO
     */
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


    /**
     * Updates an existing policy.
     *
     * @param id the ID of the policy to update
     * @param policyDTO the DTO of the policy to update
     * @return an Optional containing the updated PolicyDTO if the policy exists, or an empty Optional if not
     */
    public Optional<PolicyDTO> updatePolicy(Long id, PolicyDTO policyDTO) {
        if (!policyRepository.existsById(id)) {
            return Optional.empty();
        }
        policyDTO.setId(id);
        Policy policy = modelMapper.map(policyDTO, Policy.class);
        Policy savedPolicy = policyRepository.save(policy);
        return Optional.of(modelMapper.map(savedPolicy, PolicyDTO.class));
    }

    /**
     * Retrieves the names of available HTML templates for policy documents.
     *
     * @return a list of template names
     */
    public List<String> getTemplateNames() {
        File folder = new File("src/main/resources/templates/policy_documents/");
        File[] listOfFiles = folder.listFiles();
        return Arrays.stream(Objects.requireNonNull(listOfFiles))
                .filter(file -> file.isFile() && file.getName().endsWith(".html"))
                .map(File::getName)
                .collect(Collectors.toList());
    }

    /**
     * Generates HTML content for a policy document using a specified template and policy data.
     *
     * @param templateName the name of the HTML template
     * @param policyDTO the policy data to include in the document
     * @return the generated HTML content
     */
    public String getHtmlContent(String templateName, PolicyDTO policyDTO) {
        Context context = new Context();
        context.setVariable("policy", policyDTO);
        return templateEngine.process("policy_documents/" + templateName, context);
    }

    /**
     * Checks if a policy exists by its policy number.
     *
     * @param policyNumber the policy number to check
     * @return an Optional containing the PolicyDTOWithoutClaims if found, or an empty Optional if not found
     * Used with Claims/new Controller
     */
    public Optional<PolicyDTOWithoutClaims> checkPolicyNumber(String policyNumber) {
        return policyRepository.findByPolicyNumber(policyNumber)
                .map(policy -> modelMapper.map(policy, PolicyDTOWithoutClaims.class));
    }





}



