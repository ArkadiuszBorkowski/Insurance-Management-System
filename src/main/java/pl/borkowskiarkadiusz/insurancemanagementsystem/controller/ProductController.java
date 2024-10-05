package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.InsuranceProductService;

import java.util.List;

@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final InsuranceProductService insuranceProductService;

    @Autowired
    public ProductController(InsuranceProductService insuranceProductService) {
        this.insuranceProductService = insuranceProductService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        logger.info("Received request to fetch all products");
        List<InsuranceProductDTO> products = insuranceProductService.getAllProducts();
        logger.info("Fetched products: {}", products);
        model.addAttribute("products", products);
        return "products/products";
    }

    @GetMapping("/insuranceProduct/{id}/risks")
    @ResponseBody
    public List<RiskDTO> getRisksByProductId(@PathVariable Long id) {
        logger.info("Received request for insurance product ID: {}", id);
        List<RiskDTO> risks = insuranceProductService.getRisksByProductId(id);
        logger.info("Response: {}", risks);
        return risks;
    }

}