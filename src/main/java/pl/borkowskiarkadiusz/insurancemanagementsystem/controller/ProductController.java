package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final Map<String, String> viewNames;

    @Autowired
    public ProductController(ProductService productService, Map viewNames) {
        this.productService = productService;
        this.viewNames = viewNames;
    }

    @GetMapping()
    public String getProducts(Model model) {
        logger.info("Received request to fetch all products");
        List<InsuranceProductDTO> products = productService.getAllProducts();
        logger.info("Fetched products: {}", products);
        model.addAttribute("products", products);
        return viewNames.get("PRODUCTS_LIST");
    }

    //UŻYCIE NA FORMULARZU POLISY DO ASYNCHRONICZNEGO POBRANIA LISTY RYZYK PRZY ZMIANIE PRODUKTU W POLU TYPU SELECT.
    //DANE POBIERANE PRZEZ AJAX W SKRYPTACH JS.
    @GetMapping("/{id}/risks")
    @ResponseBody
    public List<RiskDTO> getRisksByProductId(@PathVariable Long id) {
        logger.info("Received request for insurance product ID: {}", id);
        List<RiskDTO> risks = productService.getRisksByProductId(id);
        logger.info("Response: {}", risks);
        return risks;
    }

    @GetMapping("/config")
    public String showForm(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new InsuranceProductDTO());
        model.addAttribute("allRisks", productService.getAllRisks());
        return viewNames.get("PRODUCTS_CONFIG");
    }


}