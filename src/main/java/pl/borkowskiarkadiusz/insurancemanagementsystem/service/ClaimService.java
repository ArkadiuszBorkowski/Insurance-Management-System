package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClaimsDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;

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

}
