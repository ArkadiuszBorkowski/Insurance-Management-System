package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @NotNull
    private String street;
    @NotNull
    private String streetNo;

    private String apartmentNo;
    @NotNull
    private String city;

    @NotNull
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Zipcode must be in the format XX-XXX")
    private String zipcode;


}