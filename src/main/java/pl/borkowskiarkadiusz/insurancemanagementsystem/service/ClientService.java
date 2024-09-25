package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClientDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClientRepository;

@Service
public class ClientService {

    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ModelMapper modelMapper, ClientRepository clientRepository) {
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
    }

    public ClientDTO saveClient(ClientDTO clientDTO) {
        System.out.println("Received ClientDTO: " + clientDTO);
        Client client = modelMapper.map(clientDTO, Client.class);
        System.out.println("Mapped data : " + client);
        Client savedClient = clientRepository.save(modelMapper.map(client, Client.class));
        return modelMapper.map(savedClient, ClientDTO.class);
    }
}