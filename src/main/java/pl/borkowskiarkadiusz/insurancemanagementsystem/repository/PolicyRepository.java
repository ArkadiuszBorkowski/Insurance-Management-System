package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}