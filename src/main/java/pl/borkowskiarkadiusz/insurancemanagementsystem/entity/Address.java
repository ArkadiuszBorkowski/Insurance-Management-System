package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Embeddable
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

    public Address(String street, String streetNo, String apartmentNo, String city, String zipcode) {
        this.street = street;
        this.streetNo = streetNo;
        this.apartmentNo = apartmentNo;
        this.city = city;
        this.zipcode = zipcode;
    }

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}