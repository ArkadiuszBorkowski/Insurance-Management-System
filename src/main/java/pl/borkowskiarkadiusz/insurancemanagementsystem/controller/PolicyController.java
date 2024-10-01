package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.AddressDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClientDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClientService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.InsuranceProductService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;

import java.util.List;

@Controller
class PolicyController {

    private final PolicyService policyService;
    private final InsuranceProductService insuranceProductService;
    private final ClientService clientService;

    @Autowired
    PolicyController(PolicyService policyService, InsuranceProductService insuranceProductService, ClientService clientService) {
        this.policyService = policyService;
        this.insuranceProductService = insuranceProductService;
        this.clientService = clientService;
    }


    @GetMapping("/policy/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        PolicyDTO policyDTO = policyService.getPolicyById(id);
        List<InsuranceProductDTO> productDTOs = insuranceProductService.getAllProducts();
        model.addAttribute("policy", policyDTO);
        model.addAttribute("products", productDTOs);
        return "policy";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyDTO> getPolicy(@PathVariable Long id) {
        PolicyDTO policyDTO = policyService.getPolicyById(id);
        return ResponseEntity.ok(policyDTO);
    }


    @GetMapping("/policy")
    public String getEmptyPolicyForm(Model model) {
        List<InsuranceProductDTO> productDTOs = insuranceProductService.getAllProducts();
        model.addAttribute("policy", new PolicyDTO());
        model.addAttribute("products", productDTOs);
        return "policy";
    }

/*

    @GetMapping("/policies")
    public String getPolicies(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<PolicyDTO> policyDTOPage = policyService.getPolicies(page);
        model.addAttribute("policiesPage", policyDTOPage);
        return "policies";
    }
*/

    @GetMapping("/policies")
    public String getPolicies(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) String pesel,
                              @RequestParam(required = false) String policyNumber) {
        Page<PolicyDTO> policyDTOPage = policyService.getPoliciesByPeselOrPolicyNumber(pesel, policyNumber, page);
        model.addAttribute("policiesPage", policyDTOPage);
        return "policies";
    }


    @PostMapping("/policy")
    public String savePolicy(@ModelAttribute PolicyDTO policyDTO, ClientDTO clientDTO, AddressDTO addressDTO, BindingResult result, Model model) {
        policyDTO.setClient(clientDTO);
        policyDTO.getClient().setAddress(addressDTO);
        PolicyDTO savedPolicy = policyService.savePolicy(policyDTO);
        return "redirect:/policy/" + savedPolicy.getId();
    }

}




