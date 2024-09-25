package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;


@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    /*@Pattern(regexp = "^[a-zA-Z]*$", message = "Firstname must not contain numbers or special characters")*/
    private String firstName;

    @NotNull
/*    @Pattern(regexp = "^[a-zA-Z]*$", message = "Lastname must not contain numbers or special characters")*/
    private String lastName;

/*    @Pattern(regexp = "\\d{11}", message = "PESEL must be exactly 11 digits")*/
    @NotNull
    private String pesel;

    @NotNull
    private LocalDate dateOfBirth;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
/*    @Pattern(regexp = "\\d{9,}", message = "Phone number must be at least 9 digits")*/
    private String mobileNumber;


    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    @JsonIgnore
    private Set<Policy> policies;

    public Client() {
    }

    public Client(String firstName, String lastName, String pesel, LocalDate dateOfBirth, String email, String mobileNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public @NotNull String getPesel() {
        return pesel;
    }

    public void setPesel(@NotNull String pesel) {
        this.pesel = pesel;
    }

    public @NotNull LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotNull String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(@NotNull String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}





