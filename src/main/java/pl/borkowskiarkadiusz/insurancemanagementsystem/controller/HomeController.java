package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/products")
    public String getProducts() {
        return "products3";
    }

    @GetMapping("/products2")
    public String getProducts2() {
        return "products";
    }


}
