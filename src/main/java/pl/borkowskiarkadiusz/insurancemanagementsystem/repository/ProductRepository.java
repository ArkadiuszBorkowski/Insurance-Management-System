package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Claims;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<InsuranceProduct, Long> {
    Optional<InsuranceProduct> findInsuranceProductById(Long policyId);
}