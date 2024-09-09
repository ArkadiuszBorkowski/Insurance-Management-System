package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.ClaimStatus;

@Controller
class ClaimsController {

    @GetMapping("/claimsform")
    public String getClaimsForm(Model model) {
        model.addAttribute("statuses", ClaimStatus.values());
        model.addAttribute("decisions", Decision.values());
        return "claimsform";
    }

    @GetMapping("/claims")
    public String getClaims() {
        return "claims";
    }




}
