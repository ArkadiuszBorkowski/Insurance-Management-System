package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClientDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClientService;

@Controller
@RequestMapping("/clients")
class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public String getClients() {
        return "clients";
    }

    //używane przy wyszykukiwawniu klienta podczas tworzenia polisy (zaczytanie jego danych)
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<ClientDTO> searchClientByPesel(@RequestParam String pesel) {
        logger.info("Received request for PESEL: {}", pesel);
        ClientDTO clientDTO = clientService.searchClientByPesel(pesel);
        logger.info("Response: {}", clientDTO);
        return ResponseEntity.ok(clientDTO);
    }
}


