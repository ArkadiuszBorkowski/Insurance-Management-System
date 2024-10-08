package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClaimsNumberGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static String generateClaimsNumber() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String datePart = today.format(formatter);
        int sequenceNumber = counter.getAndIncrement();
        return datePart + String.format("%03d", sequenceNumber);
    }
}
