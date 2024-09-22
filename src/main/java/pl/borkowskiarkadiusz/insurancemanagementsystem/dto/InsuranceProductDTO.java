package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import java.util.Set;

public class InsuranceProductDTO {
    private Long id;
    private String productName;
    private String description;
    private Set<RiskDTO> risks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RiskDTO> getRisks() {
        return risks;
    }

    public void setRisks(Set<RiskDTO> risks) {
        this.risks = risks;
    }

    @Override
    public String toString() {
        return "InsuranceProductDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", risks=" + risks +
                '}';
    }
}
