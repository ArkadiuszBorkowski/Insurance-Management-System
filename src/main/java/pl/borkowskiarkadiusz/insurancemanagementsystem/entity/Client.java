package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date dateOfBirth;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Pattern(regexp = "\\{9,}", message = "Phone number must be at least 9 digits")
    private String mobileNumber;


    @Embedded
    private Address address;

    @NotNull
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Policy> policies;

}





