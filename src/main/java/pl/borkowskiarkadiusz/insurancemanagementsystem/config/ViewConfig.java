package pl.borkowskiarkadiusz.insurancemanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ViewConfig {

    //KLASA DO MAPOWANIE LOKALIZACJI WIDOKÓW STRON HTML (DO KONTROLERÓW)
    @Bean
    public Map<String, String> viewNames() {
        Map<String, String> viewNames = new HashMap<>();
        viewNames.put("CLAIM_FORM", "claim/claim");
        viewNames.put("CLAIM_LIST", "claim/claims-list");
        viewNames.put("CLAIM_DETAILS", "claim/claim-details");
        viewNames.put("POLICY_FORM", "policy/policy");
        viewNames.put("POLICY_LIST", "policy/policies");
        viewNames.put("ERROR_404", "error/404");
        return viewNames;
    }
}