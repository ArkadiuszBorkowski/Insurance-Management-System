package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.Status;


import java.util.Date;

@Entity
class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer claimId;
    private String claimNumber;
    @NotNull
    private String description;
    @PastOrPresent(message = "The claim date cannot be in the future")
    @NotNull
    private Date claimDate;

    @Enumerated(EnumType.STRING)
    private Status claimStatus;

    @Enumerated(EnumType.STRING)
    private Decision decision ;






}
