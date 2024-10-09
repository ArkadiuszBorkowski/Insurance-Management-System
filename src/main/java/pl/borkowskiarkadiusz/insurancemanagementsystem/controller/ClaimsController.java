package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClaimsNumberGenerator;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/claims")
class ClaimsController {

    private final ClaimService claimService;
    private final PolicyService policyService;
    private final ClaimsNumberGenerator claimsNumberGenerator;
    private final Map<String, String> viewNames;
    private static final Logger logger = LoggerFactory.getLogger(ClaimsController.class);

    @Autowired
    public ClaimsController(ClaimService claimService, ClaimsNumberGenerator claimsNumberGenerator, PolicyService policyService, Map viewNames) {
        this.claimService = claimService;
        this.claimsNumberGenerator = claimsNumberGenerator;
        this.policyService = policyService;
        this.viewNames = viewNames;
    }

    // NOWA SZKODA
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
    // SZKODA ZAPISANA {ID}
    @GetMapping("/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        ClaimsDTO claimsDTO = claimService.getClaimsById(id);
        PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(claimsDTO.getPolicy().getId());
        initializeModelAttributes(model, claimsDTO, policyDTO);
        return viewNames.get("CLAIM_FORM");
    }

    // ZAPIS SZKODY
    @PostMapping()
    public String saveClaims(@ModelAttribute ClaimsDTO claimsDTO, @RequestParam Long policyId)  {

        PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(policyId);
        claimsDTO.setPolicy(policyDTO);
        claimsDTO.setClaimNumber(claimsNumberGenerator.generateClaimsNumber());
        claimsDTO.setClaimRegistrationDate(LocalDate.now());

        ClaimsDTO savedClaims = claimService.saveClaims(claimsDTO);
        return "redirect:/claims/" + savedClaims.getId();
    }


}
