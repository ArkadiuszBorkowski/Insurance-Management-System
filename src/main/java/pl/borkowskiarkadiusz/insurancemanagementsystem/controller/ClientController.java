package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
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


/*    @GetMapping("/client/search")
    public String searchClientByPesel(@RequestParam String pesel, Model model) {
        Client client = clientRepository.findByPesel(pesel)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PESEL: " + pesel));
        model.addAttribute("client", client);
        model.addAttribute("policy", new Policy());
        return "policy";
    }*/

/*    @GetMapping("/client/search")
    public String searchClientByPesel(@RequestParam String pesel, Model model) {
        Client client = clientRepository.findByPesel(pesel)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PESEL: " + pesel));
        Policy policy = new Policy();
        policy.setClient(client);
        model.addAttribute("policy", policy);
        return "policy"; // Zwróć nazwę widoku formularza
    }*/

    @GetMapping("/client/search")
    public String searchClientByPesel(@RequestParam String pesel, @ModelAttribute Policy policy, Model model) {
        Client client = clientRepository.findByPesel(pesel)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PESEL: " + pesel));
        policy.setClient(client);
        model.addAttribute("policy", policy);
        return "policy"; // Zwróć nazwę widoku formularza
    }

}


