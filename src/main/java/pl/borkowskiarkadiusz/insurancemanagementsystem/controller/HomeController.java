package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.time.LocalDate;
import java.util.Map;

@Controller
class HomeController {

    private final PolicyRepository policyRepository;
    private final Map<String, String> viewNames;
    private final ClaimsRepository claimsRepository;

    public HomeController(PolicyRepository policyRepository, Map<String, String> viewNames, ClaimsRepository claimsRepository) {
        this.policyRepository = policyRepository;
        this.viewNames = viewNames;
        this.claimsRepository = claimsRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String home(Model model) {
        Long policyCounter = policyRepository.count();
        Long claimsCounter = claimsRepository.count();
        Long closedClaimsCounter = claimsRepository.countClosedClaims();
        Long todayClaimsCount = claimsRepository.countClaimsRegisteredToday(LocalDate.now());
        Long paidClaimsCounter = claimsRepository.countPaidClaims();

        model.addAttribute("policyCounter", policyCounter);
        model.addAttribute("claimsCounter", claimsCounter);
        model.addAttribute("closedClaimsCounter", closedClaimsCounter);
        model.addAttribute("todayClaimsCount", todayClaimsCount);
        model.addAttribute("paidClaimsCounter", paidClaimsCounter);
        return viewNames.get("HOME_SITE");
    }

    @GetMapping("/info")
    public String info() {
        return viewNames.get("INFO_SITE");
    }
}
