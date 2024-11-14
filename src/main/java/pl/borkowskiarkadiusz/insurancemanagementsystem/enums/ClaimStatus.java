package pl.borkowskiarkadiusz.insurancemanagementsystem.enums;

/**
 * Enum representing the status of a claim in the system.
 * This enum includes various stages a claim can go through, from new claim to closed.
 */
public enum ClaimStatus {
    NOWE_ROSZCZENIE,
    WERYFIKACJA,
    OCZEKIWANIE_NA_DOKUMENTY,
    W_TRAKCIE_REALIZACJI,
    ZATWIERDZONE,
    ODRZUCONE,
    OCZEKIWANIE_NA_PŁATNOŚĆ,
    WYPŁACONE,
    ZAMKNIĘTE
}