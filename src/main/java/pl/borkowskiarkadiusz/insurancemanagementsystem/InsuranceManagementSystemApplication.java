package pl.borkowskiarkadiusz.insurancemanagementsystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InsuranceManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceManagementSystemApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
