package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClientDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClientRepository;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;

    }

    public ClientDTO searchClientByPesel(String pesel) {
        logger.debug("Searching for client with PESEL: {}", pesel);
        Client client = clientRepository.findByPesel(pesel)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PESEL: " + pesel));
        logger.debug("Found client: {}", client);
        return modelMapper.map(client, ClientDTO.class);
    }

}