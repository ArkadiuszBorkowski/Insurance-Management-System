package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.Map;

/**
 * Controller for handling client-related requests.
 */

@Controller
@RequestMapping("/clients")
class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;
    private final Map<String, String> viewNames;

    /**
     * Constructs a ClientController with the specified services and view names.
     *
     * @param clientService the service for handling clients
     * @param viewNames the map of view names
     */
    public ClientController(ClientService clientService, Map<String, String> viewNames) {
        this.clientService = clientService;
        this.viewNames = viewNames;
    }

    /**
     * Displays the form for creating a new client.
     * <p>
     * Note: This feature is currently a work in progress.
     *
     * @param model the model to hold attributes
     * @return the view name for the client form
     */
    @GetMapping("/new")
    public String newClients(Model model) {
        return viewNames.get("CLIENTS_FORM");
    }

    /**
     * Displays a list of clients.
     *
     * @param model the model to hold attributes
     * @param page the page number for pagination
     * @param pesel the PESEL number for filtering clients
     * @param lastName the last name for filtering clients
     * @param sortBy the field to sort by
     * @return the view name for the clients list
     */

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

    /**
     * Searches for a client by PESEL number.
     * <p>
     * Used during the creation of a policy to retrieve client data by entering the PESEL number into a special field.
     * If the client is found, the data is fetched via JavaScript from the API (in JSON format) and populated into the form fields.
     *
     * @param pesel the PESEL number of the client
     * @return the ResponseEntity containing the client data transfer object
     */
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<ClientDTO> searchClientByPesel(@RequestParam String pesel) {
        logger.info("Received request for PESEL: {}", pesel);
        ClientDTO clientDTO = clientService.searchClientByPesel(pesel);
        logger.info("Response: {}", clientDTO);
        return ResponseEntity.ok(clientDTO);
    }
}


