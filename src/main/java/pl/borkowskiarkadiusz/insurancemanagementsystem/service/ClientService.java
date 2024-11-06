package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClientDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<ClientDTO> getClientById(Long id) {
        return clientRepository.findById(id)
                .map(client -> modelMapper.map(client, ClientDTO.class));
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    public Page<ClientDTO> getClientsByPeselOrLastName(String pesel, String lastName, String sortBy, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy));
        Page<Client> clientsPage = clientRepository.findByPeselContainingOrLastNameContaining(pesel, lastName, pageable);
        return clientsPage.map(client -> modelMapper.map(client, ClientDTO.class));
    }

}