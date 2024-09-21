package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;

@Controller
class ClaimsController {

    @GetMapping("/claim")
    public String getClaimsForm(Model model) {
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        return "claim";
    }

    @GetMapping("/claims")
    public String getClaims() {
        return "claims";
    }




}
