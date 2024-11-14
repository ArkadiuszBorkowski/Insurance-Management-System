package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.InternalServerErrorException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PdfService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.PolicyService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ProductService;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.generator.PolicyNumberGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling policy-related requests.
 */
@Controller
public class PolicyController {

    private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);

    private final PolicyService policyService;
    private final ProductService productService;
    private final PdfService pdfService;
    private final Map<String, String> viewNames;

    /**
     * Constructs a PolicyController with the specified services and view names.
     *
     * @param policyService the service for handling policies
     * @param productService the service for handling products
     * @param pdfService the service for generating PDFs
     * @param viewNames the map of view names
     */
    public PolicyController(PolicyService policyService, ProductService productService, PdfService pdfService, Map<String, String> viewNames) {
        this.policyService = policyService;
        this.productService = productService;
        this.pdfService = pdfService;
        this.viewNames = viewNames;
    }

    /**
     * Checks if a policy number exists.
     * Used on the claims form (claims/new) to verify if a policy exists (via JavaScript).
     *
     * @param policyNumber the policy number to check
     * @return the ResponseEntity containing the policy data transfer object without claims
     */
    @GetMapping("/checkPolicyNumber")
    @ResponseBody
    public ResponseEntity<PolicyDTOWithoutClaims> checkPolicyNumber(@RequestParam String policyNumber) {
        Optional<PolicyDTOWithoutClaims> policyDTO = policyService.checkPolicyNumber(policyNumber);
        return policyDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Displays the form for creating a new policy.
     *
     * @param model the model to hold attributes
     * @return the view name for the policy form
     */
    @GetMapping("/policy")
    public String getEmptyPolicyForm(Model model) {
        try {
            List<InsuranceProductDTO> productDTOs = productService.getAllProducts(); //wczytanie produktów do listy rozwijanej
            model.addAttribute("policy", new PolicyDTO());
            model.addAttribute("products", productDTOs);
            return viewNames.get("POLICY_FORM");
        } catch (Exception e) {
            logger.error("Error loading insurance products", e);
            model.addAttribute("errorCode", "500");
            model.addAttribute("errorMessage", "Problem z załadowaniem listy produktów");
            return viewNames.get("ERROR_500");
        }
    }

    /**
     * Saves a new policy.
     *
     * @param policyDTO the policy data transfer object
     * @param clientDTO the client data transfer object
     * @param addressDTO the address data transfer object
     * @param model the model to hold attributes
     * @return the redirect URL to the saved policy or the error page if an error occurs
     */
    @PostMapping("/policy")
    public String savePolicy(@ModelAttribute PolicyDTO policyDTO, ClientDTO clientDTO, AddressDTO addressDTO, Model model) {
        try {
            policyDTO.setClient(clientDTO);
            policyDTO.getClient().setAddress(addressDTO);
            policyDTO.setReserveAmount(policyDTO.getCoverageAmount());
            policyDTO.updatePolicyStatus();  //status ustalany na podstawie warunków
            policyDTO.setPolicyNumber(PolicyNumberGenerator.generatePolicyNumber());  //numer generowany automatycznie

            PolicyDTO savedPolicy = policyService.savePolicy(policyDTO);
            return "redirect:/policy/" + savedPolicy.getId();
        } catch (Exception e) {
            logger.error("Error saving policy: {}", policyDTO, e);
            model.addAttribute("errorCode", "500");
            model.addAttribute("errorMessage", "Wystąpił nieoczekiwany błąd podczas zapisywania polisy");
            return viewNames.get("ERROR_500");
        }
    }

    /**
     * Updates an existing policy.
     *
     * @param id the ID of the policy
     * @param policyDTO the policy data transfer object
     * @param clientDTO the client data transfer object
     * @param addressDTO the address data transfer object
     * @param model the model to hold attributes
     * @return the redirect URL to the updated policy or the error page if an error occurs
     */
    @PostMapping("/policy/{id}")
    public String updatePolicy(@PathVariable Long id, @ModelAttribute PolicyDTO policyDTO, ClientDTO clientDTO, AddressDTO addressDTO, Model model) {
        try {
            PolicyDTO existingPolicy = policyService.getPolicyById(id);
            ClientDTO existingClient = existingPolicy.getClient();
            existingPolicy.setClient(clientDTO);
            existingPolicy.getClient().setAddress(addressDTO);

            Optional<PolicyDTO> updatedPolicyOpt = policyService.updatePolicy(id, existingPolicy);
            if (updatedPolicyOpt.isPresent()) {
                PolicyDTO updatedPolicy = updatedPolicyOpt.get();
                return "redirect:/policy/" + updatedPolicy.getId();
            } else {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorMessage", "Polisa o podanym ID nie została znaleziona");
                return viewNames.get("ERROR_404");
            }
        } catch (InternalServerErrorException e) {
            logger.error("Error updating policy: {}", policyDTO, e);
            model.addAttribute("errorCode", "500");
            model.addAttribute("errorMessage", "Wystąpił nieoczekiwany błąd podczas aktualizacji polisy");
            return viewNames.get("ERROR_500");
        }
    }

    /**
     * Displays a list of policies.
     *
     * @param model the model to hold attributes
     * @param page the page number for pagination
     * @param pesel the PESEL number for filtering policies
     * @param policyNumber the policy number for filtering policies
     * @param sortBy the field to sort by
     * @return the view name for the policies list
     */
    @GetMapping("/policies")
    public String getPolicies(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) String pesel,
                              @RequestParam(required = false) String policyNumber,
                              @RequestParam(defaultValue = "policyNumber") String sortBy) {
        Page<PolicyDTO> policyDTOPage = policyService.getPoliciesByPeselOrPolicyNumber(pesel, policyNumber, sortBy, page);
        model.addAttribute("policiesPage", policyDTOPage);
        model.addAttribute("pesel", pesel);
        model.addAttribute("policyNumber", policyNumber);
        model.addAttribute("sortBy", sortBy);
        return viewNames.get("POLICY_LIST");
    }

    /**
     * Displays the form for editing an existing policy.
     *
     * @param id the ID of the policy
     * @param model the model to hold attributes
     * @return the view name for the policy form or the error page if the policy is not found
     */
    @GetMapping("/policy/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        try {
            PolicyDTO policyDTO = policyService.getPolicyById(id);
            List<InsuranceProductDTO> productDTOs = productService.getAllProducts();
            List<String> templateNames = policyService.getTemplateNames();
            model.addAttribute("policy", policyDTO);
            model.addAttribute("products", productDTOs);
            model.addAttribute("templateNames", templateNames);
            return viewNames.get("POLICY_FORM");
        } catch (ResourceNotFoundException e) {
            logger.warn("Policy not found: {}", id, e);
            model.addAttribute("errorCode", "404");
            model.addAttribute("errorMessage", e.getMessage());
            return viewNames.get("ERROR_404");
        } catch (InternalServerErrorException e) {
            logger.error("Unexpected error occurred while fetching policy: {}", id, e);
            model.addAttribute("errorCode", "500");
            model.addAttribute("errorMessage", "Wystąpił nieoczekiwany błąd");
            return viewNames.get("ERROR_500");
        }
    }

    /**
     * Loads policy templates for generating documents using Thymeleaf.
     * Loaded in the DOCUMENTS section of the policy view.
     *
     * @param templateName the name of the template
     * @param policyId the ID of the policy
     * @param model the model to hold attributes
     * @return the view name for the policy document template
     */
    @GetMapping("/templates/policy_documents/{templateName}")
    public String getTemplate(@PathVariable String templateName, @RequestParam Long policyId, Model model) {
        PolicyDTO policyDTO = policyService.getPolicyById(policyId);
        model.addAttribute("policy", policyDTO);
        return "policy_documents/" + templateName;
    }


    @GetMapping("/policy/{id}/download/pdf")
    public ResponseEntity<byte[]> downloadPolicyPdf(@PathVariable Long id, @RequestParam String templateName) throws IOException {
        PolicyDTO policyDTO = policyService.getPolicyById(id);
        String htmlContent = policyService.getHtmlContent(templateName, policyDTO);


        byte[] pdfBytes = pdfService.generatePdfFromHtml(htmlContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "policy_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}




