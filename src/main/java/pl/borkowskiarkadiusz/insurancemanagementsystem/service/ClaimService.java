package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import io.micrometer.common.util.StringUtils;
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
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
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
    private final PolicyRepository policyRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public ClaimService(ClaimsRepository claimsRepository, ModelMapper modelMapper, PolicyRepository policyRepository, ApplicationEventPublisher eventPublisher) {
        this.claimsRepository = claimsRepository;
        this.modelMapper = modelMapper;
        this.policyRepository = policyRepository;
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
            //.setClaimStatus(ClaimStatus.valueOf("WYPŁACONE"));

            /*Optional<Policy> policyOpt = policyRepository.findById(existingClaim.getPolicy().getId());
            if (policyOpt.isPresent()) {
                Policy policy = policyOpt.get();
                policy.setReserveAmount(policy.getReserveAmount() - existingClaim.getPaymentAmount());
                policyRepository.save(policy);
            }*/
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




    public void updateClaimsDetailsAndState (Claims existingClaim, ClaimsDTO claimsDTO, String paymentAmount) {
        LocalDate claimDate = claimsDTO.getClaimDate();
        LocalDate policyEndDate = existingClaim.getPolicy().getEndDate();
        LocalDate claimRegistrationDate = claimsDTO.getClaimRegistrationDate();
        double claimReserveAmount = claimsDTO.getPolicy().getReserveAmount();
        double claimPaymentAmount = Double.parseDouble(paymentAmount);

        // Warunek 1: ClaimsDate > policy.EndDate
        if (claimDate.isAfter(policyEndDate)) {
            claimsDTO.setClaimStatus(ClaimStatus.ODRZUCONE);
            claimsDTO.setDecision(Decision.valueOf("ODMOWA"));
            claimsDTO.setClaimVerificationStatus("Zdarzenie ubezpieczeniowe miało miejsce po dacie wygaśnięcia polisy, w związku z czym roszczenie nie może zostać uznane.");
        } else if (claimReserveAmount == 0) {
            // Warunek 2: ClaimDate ok, ClaimReserveAmount = 0
            claimsDTO.setClaimStatus(ClaimStatus.ZAMKNIĘTE);
            claimsDTO.setDecision(Decision.valueOf("ODMOWA"));
            claimsDTO.setClaimVerificationStatus("Wyczerpana rezerwa ubezpieczeniowa - roszczenie automatycznie zamknięte.");
        } else if (claimPaymentAmount > 0) {
            // Warunek 4: ClaimDate ok, ClaimPaymentAmount > 0
            claimsDTO.setClaimStatus(ClaimStatus.WYPŁACONE);
            claimsDTO.setDecision(Decision.valueOf("AKCEPTACJA"));
            claimsDTO.setClaimVerificationStatus("Roszczenie uznane - szkoda została wypłacona.");
        } else if (existingClaim.getId() == null && claimReserveAmount == 0) {
            // Warunek 5: claims.id == null & reserveAmount == 0
            claimsDTO.setClaimVerificationStatus("Wyczerpana rezerwa ubezpieczeniowa - roszczenie nie może być wypłacone.");
        }
        // Mapowanie ClaimsDTO na Claims
        modelMapper.map(claimsDTO, existingClaim);
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
