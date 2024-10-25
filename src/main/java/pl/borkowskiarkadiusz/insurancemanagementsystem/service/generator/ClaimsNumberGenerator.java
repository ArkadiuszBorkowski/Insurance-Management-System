package pl.borkowskiarkadiusz.insurancemanagementsystem.service.generator;

import org.springframework.stereotype.Service;

@Service
public class ClaimsNumberGenerator extends NumberGenerator {

    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String PREFIX = "";

    public static String generateClaimsNumber() {
        return generateNumber(DATE_FORMAT, PREFIX);
    }
}