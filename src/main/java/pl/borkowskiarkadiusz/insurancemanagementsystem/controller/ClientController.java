package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.ClientDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClientService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clients")
class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;
    private final Map<String, String> viewNames;

    @Autowired
    public ClientController(ClientService clientService, Map<String, String> viewNames) {
        this.clientService = clientService;
        this.viewNames = viewNames;
    }
    //work in progress
    @GetMapping("/new")
    public String newClients(Model model) {
        return viewNames.get("CLIENTS_FORM");
    }

    @GetMapping()
    public String getClients(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(required = false, defaultValue = "") String pesel,
                             @RequestParam(required = false, defaultValue = "") String lastName,
                             @RequestParam(defaultValue = "lastName") String sortBy) {
        if (page < 0) {
            page = 0; // Ustawienie domyślnej wartości, jeśli indeks strony jest mniejszy niż zero
        }
        Page<ClientDTO> clientsPage = clientService.getClientsByPeselOrLastName(pesel, lastName, sortBy, page);
        model.addAttribute("clientsPage", clientsPage);
        model.addAttribute("pesel", pesel);
        model.addAttribute("lastName", lastName);
        model.addAttribute("sortBy", sortBy);
        return viewNames.get("CLIENTS_LIST");
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


