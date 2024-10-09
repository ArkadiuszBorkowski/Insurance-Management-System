package pl.borkowskiarkadiusz.insurancemanagementsystem;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class InsuranceManagementSystemApplication {

    public static void main(String[] args) {
        //log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXX started");
        SpringApplication.run(InsuranceManagementSystemApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        return new ModelMapper();
    }
}
