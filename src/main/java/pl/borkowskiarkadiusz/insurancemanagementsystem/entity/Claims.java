package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.ClaimStatus;


import java.util.Date;

@Entity
public class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;
    @PastOrPresent(message = "The claim date cannot be in the future")
    @NotNull
    private Date claimDate;

    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    @Enumerated(EnumType.STRING)
    private Decision decision ;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;




}
