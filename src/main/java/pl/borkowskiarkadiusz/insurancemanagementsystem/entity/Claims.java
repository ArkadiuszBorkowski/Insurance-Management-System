package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;


import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;
    @PastOrPresent(message = "The claim date cannot be in the future")
    @NotNull
    private LocalDate claimDate;

    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    @Enumerated(EnumType.STRING)
    private Decision decision ;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;
}
