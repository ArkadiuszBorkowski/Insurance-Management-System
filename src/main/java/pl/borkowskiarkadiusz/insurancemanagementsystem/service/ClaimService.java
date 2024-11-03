package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import io.micrometer.common.util.StringUtils;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClaimsDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.events.ClaimCreatedEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.events.ClaimPaidEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.events.ClaimUpdatedEvent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.itextpdf.styledxmlparser.jsoup.helper.StringUtil.isNumeric;

@Service
public class ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

    private final ClaimsRepository claimsRepository;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public ClaimService(ClaimsRepository claimsRepository, ModelMapper modelMapper, ApplicationEventPublisher eventPublisher) {
        this.claimsRepository = claimsRepository;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
    }

    public Optional<ClaimsDTO> getClaimsById(Long id) {
        return claimsRepository.findById(id)
                .map(claims -> modelMapper.map(claims, ClaimsDTO.class));
    }


    public ClaimsDTO saveClaims(ClaimsDTO claimsDTO) {
        try {
            Claims claims = modelMapper.map(claimsDTO, Claims.class);
            Claims savedClaims = claimsRepository.save(claims);
            eventPublisher.publishEvent(new ClaimCreatedEvent(this, savedClaims));
            return modelMapper.map(savedClaims, ClaimsDTO.class);
        } catch (Exception e) {
            logger.error("Error saving claims: {}", claimsDTO, e);
            throw new RuntimeException("Error saving claim", e);
        }
    }

    private void updateClaimDetails(Claims existingClaim, ClaimsDTO claimsDTO, String paymentAmount) {
        existingClaim.setDescription(claimsDTO.getDescription());
        existingClaim.setClaimStatus(claimsDTO.getClaimStatus());
        existingClaim.setDecision(claimsDTO.getDecision());

        //parametr przesyłany w modalu (przypisywanie płatności)
        if (StringUtils.isNotBlank(paymentAmount)) {
            Double payments = Double.parseDouble(paymentAmount);
            existingClaim.setPaymentAmount(payments);
            existingClaim.setPaymentDate(LocalDate.now());
        }
    }

    
    public ClaimsDTO updateClaims(Long id, ClaimsDTO claimsDTO, String paymentAmount) {
        Claims existingClaim = claimsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id " + id));
        updateClaimDetails(existingClaim, claimsDTO, paymentAmount);
        claimsRepository.save(existingClaim);
        processUpdateClaimsOrProcessPayment(paymentAmount, existingClaim);
        return modelMapper.map(existingClaim, ClaimsDTO.class);
    }

    private void processUpdateClaimsOrProcessPayment(String paymentAmount, Claims existingClaim) {
        if (StringUtils.isNotBlank(paymentAmount)) {
            if (isNumeric(paymentAmount)) {
                double paymentAmountValue = Double.parseDouble(paymentAmount);
                eventPublisher.publishEvent(new ClaimPaidEvent(this, existingClaim, paymentAmountValue));
            } else {
                throw new IllegalArgumentException("Invalid payment amount: " + paymentAmount);
            }
        } else {
            eventPublisher.publishEvent(new ClaimUpdatedEvent(this, existingClaim));
        }
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

    //licznik szkód w dniu dziejszym
    public long getTodayClaimsCount() {
        return claimsRepository.countClaimsRegisteredToday(LocalDate.now());
    }


}
