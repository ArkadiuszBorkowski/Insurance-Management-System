package pl.borkowskiarkadiusz.insurancemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.borkowskiarkadiusz.insurancemanagementsystem.service.ClaimService;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private ClaimService claimsService;

    @ModelAttribute("todayClaimsCount")
    public long getTodayClaimsCount() {
        return claimsService.getTodayClaimsCount();
    }
}