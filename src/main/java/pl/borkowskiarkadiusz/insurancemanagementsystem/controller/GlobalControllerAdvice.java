package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClaimService;

/**
 * Global controller advice for adding common attributes to the model.
 */

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ClaimService claimsService;

    /**
     * Retrieves the count of claims made today.
     * This is used to display the number of claims made on the current day in a special marker in the main menu next to the "CLAIMS" item.
     * This view is accessible from anywhere in the application.
     *
     * @return the count of today's claims
     */
    @ModelAttribute("todayClaimsCount")
    public long getTodayClaimsCount() {
        return claimsService.getTodayClaimsCount();
    }
}