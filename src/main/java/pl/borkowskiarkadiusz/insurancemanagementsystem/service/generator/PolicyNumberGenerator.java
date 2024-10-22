package pl.borkowskiarkadiusz.insurancemanagementsystem.service.generator;

import org.springframework.stereotype.Service;

@Service
public class PolicyNumberGenerator extends NumberGenerator {

    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String PREFIX = "BP";

    public static String generatePolicyNumber() {
        return generateNumber(DATE_FORMAT, PREFIX);
    }
}

