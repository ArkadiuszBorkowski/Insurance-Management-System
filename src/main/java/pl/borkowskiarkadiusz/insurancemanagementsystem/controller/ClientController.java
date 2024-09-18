package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.ClientRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.InsuranceProductRepository;

@Controller
class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InsuranceProductRepository insuranceProductRepository;

    @GetMapping("/clients")
    public String getClients() {
        return "clients";
    }

    @GetMapping("/client/search")
    @ResponseBody
    public ResponseEntity<Client> searchClientByPesel(@RequestParam String pesel) {
        System.out.println("Received request for PESEL: " + pesel);
        Client client = clientRepository.findByPesel(pesel)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PESEL: " + pesel));
        System.out.println("Response: " + client);
        return ResponseEntity.ok(client);
    }

/*    @GetMapping("/client/search")
    public String searchClientByPesel(@RequestParam String pesel, @ModelAttribute Policy policy, Model model) {
        Client client = clientRepository.findByPesel(pesel)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PESEL: " + pesel));
        policy.setClient(client);
        model.addAttribute("policy", policy);
        return "policy";
    }*/

}


