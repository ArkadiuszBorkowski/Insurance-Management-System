package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClaimsDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClaimService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;

import java.util.List;

@Controller
class ClaimsController {

    private final ClaimService claimService;
    private PolicyService policyService;

    @Autowired
    public ClaimsController(ClaimService claimService, PolicyService policyService) {
        this.claimService = claimService;
        this.policyService = policyService;
    }

    @GetMapping("/claim")
    public String getClaimsForm(Model model) {
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        model.addAttribute("policyStatus", PolicyStatus.values());
        model.addAttribute("claims", new ClaimsDTO());
        return "claim/claim";
    }

    @GetMapping("/claim/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        ClaimsDTO claimsDTO = claimService.getClaimsById(id);
        PolicyDTO policyDTO = policyService.getPolicyById(claimsDTO.getPolicy().getId());
        model.addAttribute("claims", claimsDTO);
        model.addAttribute("policy", policyDTO);
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        return "claim/claim";
    }



    @GetMapping("/claims")
    public String getClaims() {
        return "claim/claims";
    }




}
