package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

public class RiskDTO {
    private Long id;
    private String riskName;
    private String iconName;

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

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    @Override
    public String toString() {
        return "RiskDTO{" +
                "id=" + id +
                ", riskName='" + riskName + '\'' +
                ", iconName='" + iconName + '\'' +
                '}';
    }
}