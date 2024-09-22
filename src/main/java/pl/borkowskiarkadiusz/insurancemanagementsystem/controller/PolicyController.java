package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.InsuranceProductService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;

import java.util.List;

@Controller
class PolicyController {

    private final PolicyService policyService;
    private final InsuranceProductService insuranceProductService;

    @Autowired
    PolicyController(PolicyService policyService, InsuranceProductService insuranceProductService) {
        this.policyService = policyService;
        this.insuranceProductService = insuranceProductService;
    }


    @GetMapping("/policy/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        PolicyDTO policyDTO = policyService.getPolicyById(id);
        List<InsuranceProductDTO> productDTOs = insuranceProductService.getAllProducts();
        model.addAttribute("policy", policyDTO);
        model.addAttribute("products", productDTOs);
        return "policy";
    }


    @GetMapping("/policy")
    public String getEmptyPolicyForm(Model model) {
        List<InsuranceProductDTO> productDTOs = insuranceProductService.getAllProducts();
        model.addAttribute("policy", new PolicyDTO());
        model.addAttribute("products", productDTOs);
        return "policy";
    }


    @GetMapping("/policies")
    public String getPolicies(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<PolicyDTO> policyDTOPage = policyService.getPolicies(page);
        model.addAttribute("policiesPage", policyDTOPage);
        return "policies";
    }

    @PostMapping("/policy")
    public String savePolicy(@ModelAttribute PolicyDTO policyDTO) {
        policyService.savePolicy(policyDTO);
        return "redirect:/policies";
    }

}




