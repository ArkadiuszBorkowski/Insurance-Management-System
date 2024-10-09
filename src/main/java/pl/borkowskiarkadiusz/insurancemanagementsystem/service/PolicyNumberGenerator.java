package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class PolicyNumberGenerator {

private static final AtomicInteger counter = new AtomicInteger(1);

public static String generatePolicyNumber() {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String datePart = today.format(formatter);
    int sequenceNumber = counter.getAndIncrement();
    return "BP" + datePart + String.format("%03d", sequenceNumber);
}
}

