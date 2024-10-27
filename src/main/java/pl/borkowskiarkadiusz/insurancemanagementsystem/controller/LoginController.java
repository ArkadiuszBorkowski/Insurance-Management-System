package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {

    @GetMapping("/login")
    String loginForm() {
        return "login-form";
    }
}