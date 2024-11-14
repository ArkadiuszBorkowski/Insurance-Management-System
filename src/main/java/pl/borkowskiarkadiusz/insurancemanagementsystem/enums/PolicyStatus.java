package pl.borkowskiarkadiusz.insurancemanagementsystem.enums;

/**
 * Enum representing the status of a policy in the system.
 * This enum includes different possible statuses for a policy.
 *
 * NOWA is the default status for a new policy, but this status lasts only one day.
 * It is automatically changed by the validation schedule in the PolicyStatusScheduler class.
 */
public enum PolicyStatus {
    AKTYWNA,
    WYGASŁA,
    ZAMKNIĘTA,
    NOWA
}
