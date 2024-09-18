package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Risk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String riskName;

    private String iconName;

    @ManyToMany(mappedBy = "risks")
    @JsonBackReference
    private Set<InsuranceProduct> products;

    public Risk() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public Set<InsuranceProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<InsuranceProduct> products) {
        this.products = products;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}