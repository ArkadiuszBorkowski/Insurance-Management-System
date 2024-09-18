package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

@Controller
class HomeController {

    private final PolicyRepository policyRepository;

    public HomeController(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String home(Model model) {
        Long policyCounter = policyRepository.count();
        model.addAttribute("policyCounter", policyCounter);
        return "index";
    }



}
