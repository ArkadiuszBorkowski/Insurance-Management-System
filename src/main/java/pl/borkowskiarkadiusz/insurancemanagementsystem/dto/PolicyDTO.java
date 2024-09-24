package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import java.time.LocalDate;

public class PolicyDTO {
    private Long id;
    private String policyNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    /*private String productName;*/
    private Double premium;
    private Double coverageAmount;
    private Double reserveAmount;
    private ClientDTO client;
    private InsuranceProductDTO insuranceProduct;

    public PolicyDTO() {
    }

    public PolicyDTO(String policyNumber, LocalDate startDate, LocalDate endDate, Double premium, Double coverageAmount, Double reserveAmount, ClientDTO client, InsuranceProductDTO insuranceProduct) {
        this.policyNumber = policyNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
        this.coverageAmount = coverageAmount;
        this.reserveAmount = reserveAmount;
        this.client = client;
        this.insuranceProduct = insuranceProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public Double getReserveAmount() {
        return reserveAmount;
    }

    public void setReserveAmount(Double reserveAmount) {
        this.reserveAmount = reserveAmount;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public InsuranceProductDTO getInsuranceProduct() {
        return insuranceProduct;
    }

    public void setInsuranceProduct(InsuranceProductDTO insuranceProduct) {
        this.insuranceProduct = insuranceProduct;
    }

    @Override
    public String toString() {
        return "PolicyDTO{" +
                "id=" + id +
                ", policyNumber='" + policyNumber + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", premium=" + premium +
                ", coverageAmount=" + coverageAmount +
                ", reserveAmount=" + reserveAmount +
                ", client=" + client +
                ", insuranceProduct=" + insuranceProduct +
                '}';
    }
}
