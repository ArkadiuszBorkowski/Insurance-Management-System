package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;


@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Firstname must not contain numbers or special characters")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Lastname must not contain numbers or special characters")
    private String lastName;

    @Pattern(regexp = "\\d{11}", message = "PESEL must be exactly 11 digits")
    @NotNull
    private String pesel;

    @Temporal(TemporalType.DATE)  //dodane bo data w formiacie yyyy-mm-dd hh-mm-ss
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date dateOfBirth;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Pattern(regexp = "\\d{9,}", message = "Phone number must be at least 9 digits")
    private String mobileNumber;


    @Embedded
    private Address address;

    @NotNull
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    @JsonIgnore
    private Set<Policy> policies;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Pattern(regexp = "^[a-zA-Z]*$", message = "Firstname must not contain numbers or special characters") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull @Pattern(regexp = "^[a-zA-Z]*$", message = "Firstname must not contain numbers or special characters") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull @Pattern(regexp = "^[a-zA-Z]*$", message = "Lastname must not contain numbers or special characters") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull @Pattern(regexp = "^[a-zA-Z]*$", message = "Lastname must not contain numbers or special characters") String lastName) {
        this.lastName = lastName;
    }

    public @Pattern(regexp = "\\d{11}", message = "PESEL must be exactly 11 digits") @NotNull String getPesel() {
        return pesel;
    }

    public void setPesel(@Pattern(regexp = "\\d{11}", message = "PESEL must be exactly 11 digits") @NotNull String pesel) {
        this.pesel = pesel;
    }

    public @NotNull Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotNull @Pattern(regexp = "\\{9,}", message = "Phone number must be at least 9 digits") String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(@NotNull @Pattern(regexp = "\\{9,}", message = "Phone number must be at least 9 digits") String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public Set<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(Set<Policy> policies) {
        this.policies = policies;
    }


}





