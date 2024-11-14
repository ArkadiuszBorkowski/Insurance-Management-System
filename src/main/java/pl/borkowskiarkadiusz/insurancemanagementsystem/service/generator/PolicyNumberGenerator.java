package pl.borkowskiarkadiusz.insurancemanagementsystem.service.generator;

import org.springframework.stereotype.Service;

/**
 * Service class responsible for generating policy numbers.
 * This class extends NumberGenerator and provides a method to generate policy numbers based on the current date.
 */
@Service
public class PolicyNumberGenerator extends NumberGenerator {

    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String PREFIX = "BP";

    public static String generatePolicyNumber() {
        return generateNumber(DATE_FORMAT, PREFIX);
    }
}

