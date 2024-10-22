package pl.borkowskiarkadiusz.insurancemanagementsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ProductService;

@Component
public class StringToInsuranceProductDTOConverter implements Converter<String, InsuranceProductDTO> {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(StringToInsuranceProductDTOConverter.class);

    public StringToInsuranceProductDTOConverter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public InsuranceProductDTO convert(String source) {
        Long id = Long.valueOf(source);
        return productService.getProductById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found for id: {}", id);
                    return new IllegalArgumentException("Product not found for id: " + id);
                });
    }

}
