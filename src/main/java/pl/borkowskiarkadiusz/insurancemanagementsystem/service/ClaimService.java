package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClaimsDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
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
                .orElseThrow(() -> new ResourceNotFoundException("Invalid policy Id: " + id));
        ClaimsDTO claimsDTO = modelMapper.map(claims, ClaimsDTO.class);
        return claimsDTO;
    }


}
