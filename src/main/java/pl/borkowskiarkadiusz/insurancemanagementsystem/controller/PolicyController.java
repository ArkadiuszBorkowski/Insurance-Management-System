package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

@Controller
class PolicyController {

    @Autowired
    private PolicyRepository policyRepository;

    @GetMapping("/policy/{id}")
    public String getPolicy(@PathVariable Long id, Model model) {
        Policy policy = policyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid policy Id:" + id));
        model.addAttribute("policy", policy);
        return "policy";
    }

    @GetMapping("/policies")
    public String getPolicies() {
        return "policies";
    }

    @GetMapping("/policy")
    public String getPolicy() {
        return "policy";
    }

}
