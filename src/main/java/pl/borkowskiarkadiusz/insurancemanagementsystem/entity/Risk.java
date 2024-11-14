package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * Entity class representing a Risk in the system.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Risk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String riskName;

    private String iconName;

    @ManyToMany(mappedBy = "risks")
    @JsonBackReference
    private Set<InsuranceProduct> products;

}