package pl.borkowskiarkadiusz.insurancemanagementsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToInsuranceProductDTOConverter stringToInsuranceProductDTOConverter;

    public WebConfig(StringToInsuranceProductDTOConverter stringToInsuranceProductDTOConverter) {
        this.stringToInsuranceProductDTOConverter = stringToInsuranceProductDTOConverter;

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToInsuranceProductDTOConverter);

    }


}