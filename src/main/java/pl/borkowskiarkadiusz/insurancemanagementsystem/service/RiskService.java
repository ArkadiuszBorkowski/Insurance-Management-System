package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Risk;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.RiskRepository;

import java.util.Optional;

@Service
public class RiskService {

    @Autowired
    private RiskRepository riskRepository;

    public Optional<Risk> findById(Integer id) {
        return riskRepository.findById(id);
    }
}