package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClaimsRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.PolicyRepository;

import java.time.LocalDate;
import java.util.Map;

/**
 * Controller for handling home page and information page requests.
 */
@Controller
class HomeController {

    private final PolicyRepository policyRepository;
    private final Map<String, String> viewNames;
    private final ClaimsRepository claimsRepository;

    /**
     * Constructs a HomeController with the specified repositories and view names.
     *
     * @param policyRepository the repository for handling policies
     * @param viewNames the map of view names
     * @param claimsRepository the repository for handling claims
     */
    public HomeController(PolicyRepository policyRepository, Map<String, String> viewNames, ClaimsRepository claimsRepository) {
        this.policyRepository = policyRepository;
        this.viewNames = viewNames;
        this.claimsRepository = claimsRepository;
    }
    /**
     * Redirects to the home page.
     *
     * @return the redirect URL to the home page
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    /**
     * Displays the home page with various counters.
     *
     * @param model the model to hold attributes
     * @return the view name for the home page
     */
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

    /**
     * Displays the information page.
     * <p>
     * This page describes the entire project structure, logic, used technologies, and libraries.
     *
     * @return the view name for the information page
     */
    @GetMapping("/info")
    public String info() {
        return viewNames.get("INFO_SITE");
    }
}
