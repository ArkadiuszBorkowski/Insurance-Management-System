package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClaimService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClaimsNumberGenerator;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.InsuranceProductService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;

import java.time.LocalDate;
import java.util.List;

@Controller
class ClaimsController {

    private final ClaimService claimService;
    private PolicyService policyService;
    private ClaimsNumberGenerator claimsNumberGenerator;
    private final InsuranceProductService insuranceProductService;

    @Autowired
    public ClaimsController(ClaimService claimService, ClaimsNumberGenerator claimsNumberGenerator, PolicyService policyService, InsuranceProductService insuranceProductService) {
        this.claimService = claimService;
        this.claimsNumberGenerator = claimsNumberGenerator;
        this.policyService = policyService;
        this.insuranceProductService = insuranceProductService;
    }

    @GetMapping("/claim")
    public String getClaimsForm(Model model) {
        ClaimsDTO claimsDTO = new ClaimsDTO();
        PolicyDTO policyDTO = new PolicyDTO();
        claimsDTO.setClaimRegistrationDate(LocalDate.now());
        List<InsuranceProductDTO> productDTOs = insuranceProductService.getAllProducts();
        model.addAttribute("products", productDTOs);
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        model.addAttribute("policyStatus", PolicyStatus.values());
        return "claim/claim";
    }

        @PostMapping("/claim")
        public String saveClaims(@ModelAttribute ClaimsDTO claimsDTO, @RequestParam Long policyId, Model model) {
        PolicyDTO policyDTO = policyService.getPolicyById(policyId);
        claimsDTO.setPolicy(policyDTO);

        claimsDTO.setClaimNumber(claimsNumberGenerator.generateClaimsNumber());
        claimsDTO.setClaimRegistrationDate(LocalDate.now());

        ClaimsDTO savedClaims = claimService.saveClaims(claimsDTO);
        return "redirect:/claim/" + savedClaims.getId();
    }


    @GetMapping("/claim/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        ClaimsDTO claimsDTO = claimService.getClaimsById(id);
        PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(claimsDTO.getPolicy().getId());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        return "claim/claim";
    }

 /*   @GetMapping("/claim/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        ClaimsDTO claimsDTO = claimService.getClaimsById(id);
        PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(claimsDTO.getPolicy().getId());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        return "claim/claim";
    }*/




/*
    @GetMapping("/claim")
    public String getClaimsForm(Model model) {
        ClaimsDTO claimsDTO = new ClaimsDTO();
        PolicyDTO policyDTO = new PolicyDTO();
        claimsDTO.setClaimRegistrationDate(LocalDate.now());

        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        model.addAttribute("policyStatus", PolicyStatus.values());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        return "claim/claim";
    }
*/


/*

    @GetMapping("/claim/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        ClaimsDTO claimsDTO = claimService.getClaimsById(id);
        PolicyDTOWithoutClaims policyDTO = policyService.getPolicyById(claimsDTO.getPolicy().getId());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        return "claim/claim";
    }
*/

/*    @PostMapping("/claim")
    public String saveClaims(@ModelAttribute ClaimsDTO claimsDTO, PolicyDTO policyDTO, Model model) {
        claimsDTO.setClaimNumber(claimsNumberGenerator.generateClaimsNumber());
        claimsDTO.setClaimRegistrationDate(LocalDate.now());

        // Logowanie obiektu ClaimsDTO przed zapisem
        System.out.println("Saving ClaimsDTO: " + claimsDTO);

        ClaimsDTO savedClaims = claimService.saveClaims(claimsDTO);
        return "redirect:/claim" + savedClaims.getId();
    }*/



/*    @GetMapping("/claims")
    public String getClaims() {
        return "claim/claims";
    }*/




}
