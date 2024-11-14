package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ProductService;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling product-related requests.
 * <p>
 * Secured by Spring Security.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final Map<String, String> viewNames;

    /**
     * Constructs a ProductController with the specified services and view names.
     *
     * @param productService the service for handling products
     * @param viewNames the map of view names
     */
    public ProductController(ProductService productService, Map viewNames) {
        this.productService = productService;
        this.viewNames = viewNames;
    }

    /**
     * Displays a dynamic list of all products.
     *
     * @param model the model to hold attributes
     * @return the view name for the products list
     */
    @GetMapping()
    public String getProducts(Model model) {
        logger.info("Received request to fetch all products");
        List<InsuranceProductDTO> products = productService.getAllProducts();
        logger.info("Fetched products: {}", products);
        model.addAttribute("products", products);
        return viewNames.get("PRODUCTS_LIST");
    }

    /**
     * Retrieves a list of risks associated with a specific product.
     * Used on the policy form to asynchronously fetch the list of risks when the product is changed in the select field.
     * Data is fetched in JavaScript scripts.
     *
     * @param id the ID of the product
     * @return the list of risks associated with the product
     */
    @GetMapping("/{id}/risks")
    @ResponseBody
    public List<RiskDTO> getRisksByProductId(@PathVariable Long id) {
        logger.info("Received request for insurance product ID: {}", id);
        List<RiskDTO> risks = productService.getRisksByProductId(id);
        logger.info("Response: {}", risks);
        return risks;
    }

    /**
     * Displays the product configuration form.
     * Access to this area is restricted to users with the "ADMIN" role as configured in the SecurityConfig class.
     *
     * @param model the model to hold attributes
     * @return the view name for the product configuration form
     */

    @GetMapping("/config")
    public String showForm(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new InsuranceProductDTO());
        model.addAttribute("allRisks", productService.getAllRisks());
        return viewNames.get("PRODUCTS_CONFIG");
    }


}