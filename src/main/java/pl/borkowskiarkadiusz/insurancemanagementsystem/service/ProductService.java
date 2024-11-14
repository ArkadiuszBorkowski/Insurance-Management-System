package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Risk;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ProductRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.RiskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class responsible for managing insurance products and risks.
 * This class provides methods to retrieve all products, get a product by its ID, and get risks associated with a product.
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final RiskRepository riskRepository;
    private final ModelMapper modelMapper;

    /**
     * Constructs a new ProductService with the specified dependencies.
     * @param productRepository the repository for managing products
     * @param riskRepository the repository for managing risks
     * @param modelMapper the model mapper for converting between entities and DTOs
     */
    public ProductService(ProductRepository productRepository, RiskRepository riskRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.riskRepository = riskRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all insurance products.
     * @return a list of InsuranceProductDTOs for all products
     */
    public List<InsuranceProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        Iterable<InsuranceProduct> products = productRepository.findAll();
        List<InsuranceProductDTO> productDTOs = StreamSupport.stream(products.spliterator(), false)
                .map(product -> modelMapper.map(product, InsuranceProductDTO.class))
                .collect(Collectors.toList());
        logger.info("Fetched {} products", productDTOs.size());
        return productDTOs;
    }

    /**
     * Retrieves an insurance product by its ID.
     * @param id the ID of the product to retrieve
     * @return an Optional containing the InsuranceProductDTO if found, or an empty Optional if not found
     */
    public Optional<InsuranceProductDTO> getProductById(long id) {
        return productRepository.findById(id).map(product -> modelMapper.map(product, InsuranceProductDTO.class));
    }

    /**
     * Retrieves the risks associated with a specific product by its ID.
     * @param id the ID of the product
     * @return a list of RiskDTOs for the risks associated with the product
     */
    public List<RiskDTO> getRisksByProductId(Long id) {
        logger.debug("Searching for insurance product with ID: {}", id);
        InsuranceProduct product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        logger.debug("Found insurance product: {}", product);
        return product.getRisks().stream()
                .map(risk -> modelMapper.map(risk, RiskDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all risks.
     * @return a list of RiskDTOs for all risks
     */
    public List<RiskDTO> getAllRisks() {
        Iterable<Risk> risks = riskRepository.findAll();
        return StreamSupport.stream(risks.spliterator(), false)
                .map(risk -> modelMapper.map(risk, RiskDTO.class))
                .collect(Collectors.toList());
    }
}


