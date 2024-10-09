package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.InsuranceProductService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PdfService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyNumberGenerator;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
class PolicyController {

    private final PolicyService policyService;
    private final InsuranceProductService insuranceProductService;
    private final PdfService pdfService;

    @Autowired
    PolicyController(PolicyService policyService, InsuranceProductService insuranceProductService, PdfService pdfService) {
        this.policyService = policyService;
        this.insuranceProductService = insuranceProductService;
        this.pdfService = pdfService;
    }

    @GetMapping("/policy/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        PolicyDTO policyDTO = policyService.getPolicyById(id);
        List<InsuranceProductDTO> productDTOs = insuranceProductService.getAllProducts();
        List<String> templateNames = policyService.getTemplateNames();
        policyDTO.updatePolicyStatus();
        model.addAttribute("policy", policyDTO);
        model.addAttribute("products", productDTOs);
        model.addAttribute("templateNames", templateNames);

        return "policy/policy";
    }

    @GetMapping("/templates/policy_documents/{templateName}")
    public String getTemplate(@PathVariable String templateName, @RequestParam Long policyId, Model model) {
        PolicyDTO policyDTO = policyService.getPolicyById(policyId);
        model.addAttribute("policy", policyDTO);
        return "policy_documents/" + templateName;
    }

    //pobieranie formularza polisy
    @GetMapping("/policy")
    public String getEmptyPolicyForm(Model model) {
        List<InsuranceProductDTO> productDTOs = insuranceProductService.getAllProducts();
        model.addAttribute("policy", new PolicyDTO());
        model.addAttribute("policyStatusList", PolicyStatus.values());
        model.addAttribute("products", productDTOs);
        return "policy/policy";
    }

    //pobieranie listy polis
    @GetMapping("/policies")
    public String getPolicies(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) String pesel,
                              @RequestParam(required = false) String policyNumber) {
        Page<PolicyDTO> policyDTOPage = policyService.getPoliciesByPeselOrPolicyNumber(pesel, policyNumber, page);
        model.addAttribute("policiesPage", policyDTOPage);
        return "policy/policies";
    }

    //zapisywanie formularza
    @PostMapping("/policy")
    public String savePolicy(@ModelAttribute PolicyDTO policyDTO, ClientDTO clientDTO, AddressDTO addressDTO, BindingResult result, Model model) {
        policyDTO.setClient(clientDTO);
        policyDTO.getClient().setAddress(addressDTO);
        policyDTO.updatePolicyStatus();
        policyDTO.setPolicyNumber(PolicyNumberGenerator.generatePolicyNumber());
        PolicyDTO savedPolicy = policyService.savePolicy(policyDTO);
        return "redirect:/policy/" + savedPolicy.getId();
    }

    @GetMapping("/download/pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam String templateName, @RequestParam Long policyId) throws IOException {
        PolicyDTO policyDTO = policyService.getPolicyById(policyId);
        String htmlContent = policyService.getHtmlContent(templateName, policyDTO);

        byte[] pdfBytes = pdfService.generatePdfFromHtml(htmlContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "document.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    @GetMapping("/checkPolicyNumber")
    @ResponseBody
    public ResponseEntity<PolicyDTOWithoutClaims> checkPolicyNumber(@RequestParam String policyNumber) {
        Optional<PolicyDTOWithoutClaims> policyDTO = policyService.checkPolicyNumber(policyNumber);
        return policyDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}




