package pl.borkowskiarkadiusz.insurancemanagementsystem;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Insurance Management System.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication
@EnableScheduling
public class InsuranceManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceManagementSystemApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return new ModelMapper();
    }
}
