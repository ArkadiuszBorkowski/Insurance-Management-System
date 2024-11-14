package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Controller for handling claims-related requests.
 */

@Controller
@RequestMapping("/claims")
class ClaimsController {

    private final ClaimService claimService;
    private final PolicyService policyService;
    private final Map<String, String> viewNames;

    private static final Logger logger = LoggerFactory.getLogger(ClaimsController.class);

    /**
     * Constructs a ClaimsController with the specified services and view names.
     *
     * @param claimService the service for handling claims
     * @param policyService the service for handling policies
     * @param viewNames the map of view names
     */

    public ClaimsController(ClaimService claimService, PolicyService policyService, Map viewNames) {
        this.claimService = claimService;
        this.policyService = policyService;
        this.viewNames = viewNames;
    }

    /**
     * Displays the form for creating a new claim.
     *
     * @param model the model to hold attributes
     * @return the view name for the claim form
     */
    @GetMapping("/new")
    public String newClaim(Model model) {
        ClaimsDTO claimsDTO = new ClaimsDTO();
        PolicyDTOWithoutClaims policyDTO = new PolicyDTO();
        claimsDTO.initializeDefaultValues();
        initializeModelAttributes(model, claimsDTO, policyDTO);
        return viewNames.get("CLAIM_FORM");
    }

    /**
     * Initializes model attributes for the claim form.
     *
     * @param model the model to hold attributes
     * @param claimsDTO the claims data transfer object
     * @param policyDTO the policy data transfer object
     */
    private void initializeModelAttributes(Model model, ClaimsDTO claimsDTO, PolicyDTOWithoutClaims policyDTO) {
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        model.addAttribute("policyStatus", PolicyStatus.values());
    }

    /**
     * Displays the form for editing and update an existing claim.
     *
     * @param id the ID of the claim
     * @param model the model to hold attributes
     * @return the view name for the claim form or error page if the claim is not found
     */
    @GetMapping("/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        Optional<ClaimsDTO> claimsDtoOptional = claimService.getClaimsById(id);

        if (claimsDtoOptional.isPresent()) {
            ClaimsDTO claimsDTO = claimsDtoOptional.get();
            PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(claimsDTO.getPolicy().getId());
            initializeModelAttributes(model, claimsDTO, policyDTO);
            return viewNames.get("CLAIM_FORM");
        } else {
            model.addAttribute("errorMessage", "Roszczenie o ID = " + id + " nie zostało znalezione.");
            return viewNames.get("ERROR_404");
        }
    }

    /**
     * Saves a new claim.
     *
     * @param claimsDTO the claims data transfer object
     * @param bindingResult the binding result for validation errors
     * @param model the model to hold attributes
     * @return the redirect URL to the saved claim or the claim form if there are validation errors
     */

    @PostMapping
    public String saveClaim(@ModelAttribute("claims") @Valid ClaimsDTO claimsDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: {}", bindingResult.getAllErrors());
            return viewNames.get("CLAIM_FORM");
        }
        claimsDTO.setClaimNumber(ClaimsNumberGenerator.generateClaimsNumber());
        claimsDTO.setClaimRegistrationDate(LocalDate.now());
        claimsDTO.initializeDefaultValues();

        ClaimsDTO savedClaims = claimService.saveClaims(claimsDTO);
        return "redirect:/claims/" + savedClaims.getId();
    }

    /**
     * Updates an existing claim.
     *
     * @param id the ID of the claim
     * @param claimsDTO the claims data transfer object
     * @param paymentAmountModal the payment amount from the modal
     * @param result the binding result for validation errors
     * @return the redirect URL to the updated claim
     */
    @PostMapping("/{id}")
    public String updateClaims(@PathVariable Long id, @ModelAttribute ClaimsDTO claimsDTO, @RequestParam("paymentAmountModal") String paymentAmountModal, BindingResult result) {

        ClaimsDTO updatedClaims = claimService.updateClaims(id, claimsDTO, paymentAmountModal);
        return "redirect:/claims/" + updatedClaims.getId();
    }


    /**
     * Displays a list of claims.
     *
     * @param model the model to hold attributes
     * @param page the page number for pagination
     * @param pesel the PESEL number for filtering claims
     * @param claimNumber the claim number for filtering claims
     * @param sortBy the field to sort by
     * @return the view name for the claims list
     */
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
