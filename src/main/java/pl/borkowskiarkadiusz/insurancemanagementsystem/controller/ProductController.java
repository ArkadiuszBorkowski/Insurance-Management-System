package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.InsuranceProductRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private InsuranceProductRepository insuranceProductRepository;


    @GetMapping("/products")
    public String getProducts(Model model) {
        Iterable<InsuranceProduct> iterableProducts = insuranceProductRepository.findAll();
        List<InsuranceProduct> products = new ArrayList<>();
        iterableProducts.forEach(products::add);
        model.addAttribute("products", products);
        return "products";
    }
}