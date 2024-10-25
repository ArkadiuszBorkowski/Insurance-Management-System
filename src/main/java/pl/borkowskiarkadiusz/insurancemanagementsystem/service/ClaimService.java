package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClaimsDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

    private final ClaimsRepository claimsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClaimService(ClaimsRepository claimsRepository, ModelMapper modelMapper) {
        this.claimsRepository = claimsRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<ClaimsDTO> getClaimsById(Long id) {
        return claimsRepository.findById(id)
                .map(claims -> modelMapper.map(claims, ClaimsDTO.class));
    }

    public ClaimsDTO saveClaims(ClaimsDTO claimsDTO) {
        try {
            Claims claims = modelMapper.map(claimsDTO, Claims.class);
            Claims savedClaims = claimsRepository.save(claims);
            return modelMapper.map(savedClaims, ClaimsDTO.class);
        } catch (Exception e) {
            logger.error("Error saving claims: {}", claimsDTO, e);
            throw new RuntimeException("Error saving claim", e);
        }
    }

    private void updateClaimDetails(Claims existingClaim, ClaimsDTO claimsDTO) {
        existingClaim.setDescription(claimsDTO.getDescription());
        existingClaim.setClaimStatus(claimsDTO.getClaimStatus());
        existingClaim.setDecision(claimsDTO.getDecision());
    }

    public ClaimsDTO updateClaims(Long id, ClaimsDTO claimsDTO) {
        Claims existingClaim = claimsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id " + id));

        updateClaimDetails(existingClaim, claimsDTO);
        claimsRepository.save(existingClaim);
        return modelMapper.map(existingClaim, ClaimsDTO.class);
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
