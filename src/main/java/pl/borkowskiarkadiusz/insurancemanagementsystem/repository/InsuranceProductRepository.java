package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;

public interface InsuranceProductRepository extends CrudRepository<InsuranceProduct, Long> {
}