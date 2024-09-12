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

    @ManyToOne
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "policy")
    private Set<Claims> claims;

    @AssertTrue(message = "Last date must be after or equal to first date")
    public boolean isLastDateValid() {
        return endDate == null || startDate == null || !endDate.isBefore(startDate);
    }

    public Policy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(@NotNull String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public @NotNull LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull LocalDate endDate) {
        this.endDate = endDate;
    }

    public InsuranceProduct getInsuranceProduct() {
        return insuranceProduct;
    }

    public void setInsuranceProduct(InsuranceProduct insuranceProduct) {
        this.insuranceProduct = insuranceProduct;
    }

    public @NotNull @Positive Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(@NotNull @Positive Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public @NotNull @Positive Double getReserveAmount() {
        return reserveAmount;
    }

    public void setReserveAmount(@NotNull @Positive Double reserveAmount) {
        this.reserveAmount = reserveAmount;
    }

    public @NotNull @Positive Double getPremium() {
        return premium;
    }

    public void setPremium(@NotNull @Positive Double premium) {
        this.premium = premium;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Claims> getClaims() {
        return claims;
    }

    public void setClaims(Set<Claims> claims) {
        this.claims = claims;
    }
}
