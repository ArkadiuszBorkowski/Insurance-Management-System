package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Risk;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.RiskRepository;

import java.util.Optional;


/**
 * Service class for managing risks.
 */
@Service
public class RiskService {


    private RiskRepository riskRepository;

    public Optional<Risk> findById(Integer id) {
        return riskRepository.findById(id);
    }
}