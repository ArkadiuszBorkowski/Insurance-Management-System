package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.borkowskiarkadiusz.insurancemanagementsystem.Enum.PolicyType;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String policyNumber;

    @NotNull
    @Positive
    private Double coverageAmount;

    @NotNull
    @Positive
    private Double reserveAmount;

    @NotNull
    @Positive
    private Double premium;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate  endDate;

    @ManyToOne
    private Client client;

    @ManyToOne
    @JoinColumn(name = "insurance_product_id", referencedColumnName = "id")
    private InsuranceProduct insuranceProduct;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "policy")
    private Set<Claims> claims;

    @AssertTrue(message = "Last date must be after or equal to first date")
    public boolean isLastDateValid() {
        return endDate == null || startDate == null || !endDate.isBefore(startDate);
    }


}
