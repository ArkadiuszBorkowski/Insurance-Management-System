package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClaimsDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTOWithoutClaims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

    private final ClaimsRepository claimsRepository;
    private final PolicyService policyService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClaimService(ClaimsRepository claimsRepository, PolicyService policyService, ModelMapper modelMapper) {
        this.claimsRepository = claimsRepository;
        this.policyService = policyService;
        this.modelMapper = modelMapper;
    }

    public ClaimsDTO getClaimsById(Long id) {
        Claims claims  = claimsRepository.findById(id)
                .orElseThrow(() -> {
                            logger.error("Claims not found with id {}" , id);
                            return new ResourceNotFoundException("Claim not found with id " + id);
                        });
        ClaimsDTO claimsDTO = modelMapper.map(claims, ClaimsDTO.class);
        return claimsDTO;
    }

    public ClaimsDTO saveClaims(ClaimsDTO claimsDTO) {
        try {
            Claims claims = modelMapper.map(claimsDTO, Claims.class);
            Claims savedClaims = claimsRepository.save(claims);
            return modelMapper.map(savedClaims, ClaimsDTO.class);
        } catch (Exception e) {
            logger.error("Error saving policy: {}", claimsDTO, e);
            throw new RuntimeException("Error saving claim", e);
        }
    }

    public ClaimsDTO updateClaims(Long id, ClaimsDTO claimsDTO) {

        Claims existingClaims = claimsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Claims not found"));
        Long policyId = existingClaims.getPolicy().getId();
        modelMapper.map(claimsDTO, existingClaims);
        PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(policyId);
        existingClaims.setPolicy(modelMapper.map(policyDTO, Policy.class));
        Claims updatedClaims = claimsRepository.save(existingClaims);
        return modelMapper.map(updatedClaims, ClaimsDTO.class);
    }

    public Page<ClaimsDTO> getClaimsByPeselOrClaimNumber(String pesel, String claimNumber, String sortBy, int page) {
        Page<Claims> claimsPage;
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy));

        if (pesel != null && !pesel.isEmpty()) {
            claimsPage = claimsRepository.findByPolicyClientPesel(pesel, pageable);
        } else if (claimNumber != null && !claimNumber.isEmpty()) {
            claimsPage = claimsRepository.findByClaimNumber(claimNumber, pageable);
        } else {
            claimsPage = claimsRepository.findAll(pageable);
        }

        List<ClaimsDTO> claimsDTos = claimsPage.stream()
                .map(claim -> modelMapper.map(claim, ClaimsDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(claimsDTos, pageable, claimsPage.getTotalElements());
    }


}
