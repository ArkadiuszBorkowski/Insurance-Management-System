package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Risk;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.mapper.InsuranceProductMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.mapper.PolicyMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.mapper.RiskMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.InsuranceProductRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
class PolicyController {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private InsuranceProductRepository insuranceProductRepository;


    @GetMapping("/policy/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Invalid policy Id: " + id));
        Iterable<InsuranceProduct> products = insuranceProductRepository.findAll();
        List<InsuranceProductDTO> productDTOs = StreamSupport.stream(products.spliterator(), false)
                .map(InsuranceProductMapper::toDTO)
                .collect(Collectors.toList());
        PolicyDTO policyDTO = PolicyMapper.toDTO(policy);
        model.addAttribute("policy", policyDTO);
        model.addAttribute("products", productDTOs);
        return "policy";
    }


    @GetMapping("/policy")
    public String getEmptyPolicyForm(Model model) {
        Iterable<InsuranceProduct> products = insuranceProductRepository.findAll();
        List<InsuranceProductDTO> productDTOs = StreamSupport.stream(products.spliterator(), false)
                .map(InsuranceProductMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("policy", new PolicyDTO());
        model.addAttribute("products", products);
        return "policy";
    }


    @GetMapping("/policies")
    public String getPolicies(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Policy> policiesPage = policyRepository.findAll(PageRequest.of(page, 10));
        List<PolicyDTO> policyDTOs = policiesPage.stream()
                .map(PolicyMapper::toDTO)
                .collect(Collectors.toList());
        Page<PolicyDTO> policyDTOPage = new PageImpl<>(policyDTOs, PageRequest.of(page, 10), policiesPage.getTotalElements());
        model.addAttribute("policiesPage", policyDTOPage);
        return "policies";
    }

    @GetMapping("/insuranceProduct/{id}/risks")
    @ResponseBody
    public List<RiskDTO> getRisksByProductId(@PathVariable Long id) {
        InsuranceProduct product = insuranceProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return product.getRisks().stream()
                .map(RiskMapper::toDTO)
                .collect(Collectors.toList());
    }
}




