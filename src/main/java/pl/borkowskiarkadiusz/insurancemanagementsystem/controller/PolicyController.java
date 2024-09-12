package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
class PolicyController {

    @Autowired
    private PolicyRepository policyRepository;

/*    @GetMapping("/policies")
    public String getPolicies(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "policyNumber") String sortBy,
                              @RequestParam(defaultValue = "") String pesel,
                              @RequestParam(defaultValue = "") String policyNumber) {
        Page<Policy> policiesPage;
        if (!pesel.isEmpty()) {
            policiesPage = policyRepository.findByClientPeselContaining(pesel, PageRequest.of(page, 10, Sort.by(sortBy)));
        } else if (!policyNumber.isEmpty()) {
            policiesPage = policyRepository.findByPolicyNumberContaining(policyNumber, PageRequest.of(page, 10, Sort.by(sortBy)));
        } else {
            policiesPage = policyRepository.findAll(PageRequest.of(page, 10, Sort.by(sortBy)));
        }
        model.addAttribute("policiesPage", policiesPage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("pesel", pesel);
        model.addAttribute("policyNumber", policyNumber);
        return "policyList";
    }*/

    @GetMapping("/policy/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        Policy policy = policyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid policy Id:" + id));
        model.addAttribute("policy", policy);
        return "policy";
    }

    @GetMapping("/policy")
    public String showPolicyForm(Model model) {
        model.addAttribute("policy", new Policy());
        return "policy";
    }

    @GetMapping("/policies")
    public String getPolicies(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Policy> policiesPage = policyRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("policiesPage", policiesPage);
        return "policies";
    }
}





/*    @GetMapping("/policies")
    public String getPolicies(Model model) {
        Iterable<Policy> iterablePolicies = policyRepository.findAll();
        List<Policy> policies = new ArrayList<>();
        iterablePolicies.forEach(policies::add);
        model.addAttribute("policies", policies);
        return "policies";
    }*/

/*
    @GetMapping("/policies")
    public String getPolicies(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Policy> policiesPage = policyRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("policiesPage", policiesPage);
        return "policyList";
    }
*/



