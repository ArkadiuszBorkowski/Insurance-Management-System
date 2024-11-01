package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Risk;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ProductRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.RiskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final RiskRepository riskRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, RiskRepository riskRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.riskRepository = riskRepository;
        this.modelMapper = modelMapper;
    }

    public List<InsuranceProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        Iterable<InsuranceProduct> products = productRepository.findAll();
        List<InsuranceProductDTO> productDTOs = StreamSupport.stream(products.spliterator(), false)
                .map(product -> modelMapper.map(product, InsuranceProductDTO.class))
                .collect(Collectors.toList());
        logger.info("Fetched {} products", productDTOs.size());
        return productDTOs;
    }

    public Optional<InsuranceProductDTO> getProductById(long id) {
        return productRepository.findById(id).map(product -> modelMapper.map(product, InsuranceProductDTO.class));
    }

    public List<RiskDTO> getRisksByProductId(Long id) {
        logger.debug("Searching for insurance product with ID: {}", id);
        InsuranceProduct product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        logger.debug("Found insurance product: {}", product);
        return product.getRisks().stream()
                .map(risk -> modelMapper.map(risk, RiskDTO.class))
                .collect(Collectors.toList());
    }

    public List<RiskDTO> getAllRisks() {
        Iterable<Risk> risks = riskRepository.findAll();
        return StreamSupport.stream(risks.spliterator(), false)
                .map(risk -> modelMapper.map(risk, RiskDTO.class))
                .collect(Collectors.toList());
    }
}


