package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.mapper.RiskMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.InsuranceProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/insuranceProduct/{id}/risks")
    @ResponseBody
    public List<RiskDTO> getRisksByProductId(@PathVariable Long id) {
        InsuranceProduct product = insuranceProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return product.getRisks().stream()
                .map(RiskMapper::toDTO)
                .collect(Collectors.toList());
    }

}