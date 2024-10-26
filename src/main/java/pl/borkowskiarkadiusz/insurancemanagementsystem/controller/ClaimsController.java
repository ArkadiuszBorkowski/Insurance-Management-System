package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClaimsDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTOWithoutClaims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClaimService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.generator.ClaimsNumberGenerator;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/claims")
class ClaimsController {

    private final ClaimService claimService;
    private final PolicyService policyService;
    private final Map<String, String> viewNames;

    private static final Logger logger = LoggerFactory.getLogger(ClaimsController.class);

    @Autowired
    public ClaimsController(ClaimService claimService, PolicyService policyService, Map viewNames) {
        this.claimService = claimService;
        this.policyService = policyService;
        this.viewNames = viewNames;
    }

    //new claim
    @GetMapping("/new")
    public String newClaim(Model model) {
        ClaimsDTO claimsDTO = new ClaimsDTO();
        PolicyDTOWithoutClaims policyDTO = new PolicyDTO();
        claimsDTO.initializeDefaultValues();
        initializeModelAttributes(model, claimsDTO, policyDTO);
        return viewNames.get("CLAIM_FORM");
    }
    // NOWA / ZAPISANA SZKODA - metoda pomocnicza
    private void initializeModelAttributes(Model model, ClaimsDTO claimsDTO, PolicyDTOWithoutClaims policyDTO) {
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        model.addAttribute("policyStatus", PolicyStatus.values());
    }

    //claim {ID}
    @GetMapping("/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        Optional<ClaimsDTO> claimsDtoOptional = claimService.getClaimsById(id);
        ClaimsDTO claimsDTO = claimsDtoOptional.get();
        PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(claimsDTO.getPolicy().getId());
        initializeModelAttributes(model, claimsDTO, policyDTO);
        return viewNames.get("CLAIM_FORM");
    }

    //save - zapis danych szkody - dane polisy jak status czy rezerwa będą zmieniane procesami.
    @PostMapping
    public String saveClaim(@ModelAttribute("claims") @Valid ClaimsDTO claimsDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: {}", bindingResult.getAllErrors());
            return viewNames.get("CLAIM_FORM");
        }
        claimsDTO.initializeDefaultValues();
        claimsDTO.setClaimNumber(ClaimsNumberGenerator.generateClaimsNumber());
        claimsDTO.setClaimRegistrationDate(LocalDate.now());

        ClaimsDTO savedClaims = claimService.saveClaims(claimsDTO);
        return "redirect:/claims/" + savedClaims.getId();
    }

    //save claims
    @PostMapping("/{id}")
    public String updateClaims(@PathVariable Long id, @ModelAttribute ClaimsDTO claimsDTO, @RequestParam("paymentAmountModal") String paymentAmountModal, BindingResult result) {

        ClaimsDTO updatedClaims = claimService.updateClaims(id, claimsDTO, paymentAmountModal);
        return "redirect:/claims/" + updatedClaims.getId();
    }


    //claims list
    @GetMapping
    public String getClaims(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) String pesel,
                            @RequestParam(required = false) String claimNumber,
                            @RequestParam(defaultValue = "claimNumber") String sortBy) {
        Page<ClaimsDTO> claimsPage = claimService.getClaimsByPeselOrClaimNumber(pesel, claimNumber, sortBy, page);
        model.addAttribute("claimsPage", claimsPage);
        model.addAttribute("pesel", pesel);
        model.addAttribute("claimNumber", claimNumber);
        model.addAttribute("sortBy", sortBy);
        return viewNames.get("CLAIM_LIST");
    }

}
