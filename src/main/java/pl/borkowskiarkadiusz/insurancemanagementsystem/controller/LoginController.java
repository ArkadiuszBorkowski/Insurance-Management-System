package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling login requests.
 * Secured by Spring Security.
 */
@Controller
class LoginController {

    /**
     * Displays the login form.
     *
     * @return the view name for the login form
     */
    @GetMapping("/login")
    String loginForm() {
        return "login-form";
    }
}