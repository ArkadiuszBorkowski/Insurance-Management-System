package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class PolicyController {

    @GetMapping("/policy")
    public String getClients() {
        return "policy";
    }

}
