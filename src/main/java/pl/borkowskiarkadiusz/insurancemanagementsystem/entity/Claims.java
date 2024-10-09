package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.ClaimStatus;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.Decision;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String claimNumber;

    @NotNull
    private String description;

    @PastOrPresent(message = "The claim date cannot be in the future")
    @NotNull
    private LocalDate claimDate;

    @NotNull
    private LocalDate claimRegistrationDate;

    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    @Enumerated(EnumType.STRING)
    private Decision decision ;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    @JsonBackReference
    private Policy policy;
}
