package pl.borkowskiarkadiusz.insurancemanagementsystem.service.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract class responsible for generating sequential numbers based on the current date.
 * This class provides a method to generate numbers with a specified date format and prefix.
 */
public abstract class NumberGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);

    protected static String generateNumber(String dateFormat, String prefix) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        String datePart = today.format(formatter);
        int sequenceNumber = counter.getAndIncrement();
        return prefix + datePart + String.format("%03d", sequenceNumber);
    }
}