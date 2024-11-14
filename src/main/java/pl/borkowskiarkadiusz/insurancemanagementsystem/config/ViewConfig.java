package pl.borkowskiarkadiusz.insurancemanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for mapping view locations to HTML pages (for controllers).
 */
@Configuration
public class ViewConfig {

    /**
     * Bean for mapping view names to their corresponding paths.
     *
     * @return a map containing view names and their paths.
     */
    @Bean
    public Map<String, String> viewNames() {
        Map<String, String> viewNames = new HashMap<>();
        viewNames.put("HOME_SITE", "/index");
        viewNames.put("INFO_SITE", "/info");
        viewNames.put("LOGIN", "/login-form");
        viewNames.put("CLAIM_FORM", "claim/claim");
        viewNames.put("CLAIM_LIST", "claim/claims-list");
        viewNames.put("CLAIM_DETAILS", "claim/claim-details");
        viewNames.put("CLIENTS_LIST", "clients/clients");
        viewNames.put("CLIENTS_FORM", "clients/clients_form");
        viewNames.put("POLICY_FORM", "policy/policy");
        viewNames.put("POLICY_LIST", "policy/policies");
        viewNames.put("POLICY_DOC", "policy/documents");
        viewNames.put("PRODUCTS_LIST", "products/products");
        viewNames.put("PRODUCTS_CONFIG", "products/products-config");
        viewNames.put("ERROR_404", "error/404");
        viewNames.put("ERROR_500", "error/500");
        viewNames.put("ERROR_PAGE", "error/error-page");
        return viewNames;
    }
}