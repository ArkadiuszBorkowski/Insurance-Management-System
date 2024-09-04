package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.Status;

@Controller
public class ClaimsController {

    @GetMapping("/claims")
    public String getClaims(Model model) {
        model.addAttribute("statuses", Status.values());
        model.addAttribute("decisions", Decision.values());
        return "claims";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/test")
    public String test() {
        return "test2";
    }


}
