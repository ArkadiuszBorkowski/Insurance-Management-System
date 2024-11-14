package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import io.micrometer.common.util.StringUtils;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing claims.
 * This class provides methods to create, update, and retrieve claims, as well as to handle claim-related events.
 */
@Service
public class ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

    private final ClaimsRepository claimsRepository;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;


    /**
     * Constructs a new ClaimService with the specified dependencies.
     * @param claimsRepository the repository for managing claims
     * @param modelMapper the model mapper for converting between entities and DTOs
     * @param eventPublisher the event publisher for publishing claim-related events
     */
    public ClaimService(ClaimsRepository claimsRepository, ModelMapper modelMapper, ApplicationEventPublisher eventPublisher) {
        this.claimsRepository = claimsRepository;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
    }

    public Optional<ClaimsDTO> getClaimsById(Long id) {
        return claimsRepository.findById(id)
                .map(claims -> modelMapper.map(claims, ClaimsDTO.class));
    }

    /**
     * Saves a new claim.
     * @param claimsDTO the DTO of the claim to save
     * @return the saved ClaimsDTO
     */
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

    /**
     * Updates an existing claim.
     *
     * @param id the ID of the claim to update
     * @param claimsDTO the DTO of the claim to update
     * @param paymentAmount the payment amount to set, if applicable
     * @return the updated ClaimsDTO
     */
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

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /**
     * Retrieves claims by client's PESEL or claim number with pagination and sorting.
     * @param pesel the client's PESEL
     * @param claimNumber the claim number
     * @param sortBy the field to sort by
     * @param page the page number to retrieve
     * @return a Page of ClaimsDTOs
     */
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

    /**
     * Counts the number of claims registered today.
     * @return the number of claims registered today
     */
    public long getTodayClaimsCount() {
        return claimsRepository.countClaimsRegisteredToday(LocalDate.now());
    }


}
