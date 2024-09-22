package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.InsuranceProductDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.InsuranceProduct;
import pl.borkowskiarkadiusz.insurancemanagementsystem.mapper.InsuranceProductMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.InsuranceProductRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InsuranceProductService {

    private final InsuranceProductRepository insuranceProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InsuranceProductService(InsuranceProductRepository insuranceProductRepository, ModelMapper modelMapper) {
        this.insuranceProductRepository = insuranceProductRepository;
        this.modelMapper = modelMapper;
    }

    public List<InsuranceProductDTO> getAllProducts() {
        Iterable<InsuranceProduct> products = insuranceProductRepository.findAll();
        return StreamSupport.stream(products.spliterator(), false)
                .map(product -> modelMapper.map(product, InsuranceProductDTO.class))
                .collect(Collectors.toList());
    }
}

  /*  private final InsuranceProductRepository insuranceProductRepository;

    @Autowired
    public InsuranceProductService(InsuranceProductRepository insuranceProductRepository) {
        this.insuranceProductRepository = insuranceProductRepository;
    }

    public List<InsuranceProductDTO> getAllProducts() {
        Iterable<InsuranceProduct> products = insuranceProductRepository.findAll();
        return StreamSupport.stream(products.spliterator(), false)
                .map(InsuranceProductMapper::toDTO)
                .collect(Collectors.toList());
    }*/