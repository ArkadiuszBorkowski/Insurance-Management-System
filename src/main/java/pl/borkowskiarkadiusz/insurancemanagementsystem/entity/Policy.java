package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String policyNumber;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate  endDate;

    @ManyToOne
    @JoinColumn(name = "insurance_product_id", referencedColumnName = "id")
    private InsuranceProduct insuranceProduct;

    @NotNull
    @Positive
    private Double coverageAmount;

    @NotNull
    @Positive
    private Double reserveAmount;

    @NotNull
    @Positive
    private Double premium;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference  //dodane 16.09
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "policy")
    private Set<Claims> claims;

    @AssertTrue(message = "Last date must be after or equal to first date")
    public boolean isLastDateValid() {
        return endDate == null || startDate == null || !endDate.isBefore(startDate);
    }



}
