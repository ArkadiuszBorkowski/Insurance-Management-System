package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Policy {

    private static final Logger logger = LoggerFactory.getLogger(Policy.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 10, max = 20)
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
    @Min(0)
    private Double reserveAmount;
    @NotNull
    @Positive
    private Double premium;

    private PolicyStatus policyStatus;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private Client client;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "policy")
    @JsonManagedReference
    private Set<Claims> claims;


    @AssertTrue(message = "Last date must be after or equal to first date")
    public boolean isLastDateValid() {
        return endDate == null || startDate == null || !endDate.isBefore(startDate);
    }

    @PrePersist
    @PreUpdate
    private void logPolicyDetails() {
        logger.info("Policy details: {}", this);
    }


}
