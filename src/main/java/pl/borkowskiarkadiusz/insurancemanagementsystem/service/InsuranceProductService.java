package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.RiskDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.exceptions.ResourceNotFoundException;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.InsuranceProductRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InsuranceProductService {

    private static final Logger logger = LoggerFactory.getLogger(InsuranceProductService.class);
    private final InsuranceProductRepository insuranceProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InsuranceProductService(InsuranceProductRepository insuranceProductRepository, ModelMapper modelMapper) {
        this.insuranceProductRepository = insuranceProductRepository;
        this.modelMapper = modelMapper;
    }

    public List<InsuranceProductDTO> getAllProducts() {
        Iterable<InsuranceProduct> products = insuranceProductRepository.findAll();
        return StreamSupport.stream(products.spliterator(), false)
                .map(product -> modelMapper.map(product, InsuranceProductDTO.class))
                .collect(Collectors.toList());
    }

    public InsuranceProductDTO getProductById(Long id) {
        InsuranceProduct product = insuranceProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid product Id: " + id));
        return modelMapper.map(product, InsuranceProductDTO.class);
    }

    public List<RiskDTO> getRisksByProductId(Long id) {
        logger.debug("Searching for insurance product with ID: {}", id);
        InsuranceProduct product = insuranceProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        logger.debug("Found insurance product: {}", product);
        return product.getRisks().stream()
                .map(risk -> modelMapper.map(risk, RiskDTO.class))
                .collect(Collectors.toList());
    }
}


