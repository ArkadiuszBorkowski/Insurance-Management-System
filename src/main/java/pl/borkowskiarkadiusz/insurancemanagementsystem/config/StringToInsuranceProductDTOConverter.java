package pl.borkowskiarkadiusz.insurancemanagementsystem.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.InsuranceProductService;

@Component
public class StringToInsuranceProductDTOConverter implements Converter<String, InsuranceProductDTO> {

    private final InsuranceProductService insuranceProductService;

    public StringToInsuranceProductDTOConverter(InsuranceProductService insuranceProductService) {
        this.insuranceProductService = insuranceProductService;
    }

    @Override
    public InsuranceProductDTO convert(String source) {
        Long id = Long.valueOf(source);
        return insuranceProductService.getProductById(id);
    }
}
