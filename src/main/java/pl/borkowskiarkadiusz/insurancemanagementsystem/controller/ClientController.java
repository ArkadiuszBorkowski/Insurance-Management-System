package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ClientController {

    @GetMapping("/clients")
    public String getClients() {
        return "clients";
    }

}
