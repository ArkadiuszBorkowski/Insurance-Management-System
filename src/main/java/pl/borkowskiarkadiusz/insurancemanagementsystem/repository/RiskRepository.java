package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Risk;

public interface RiskRepository extends JpaRepository<Risk, Integer> {
}
