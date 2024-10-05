package pl.borkowskiarkadiusz.insurancemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$", message = "Firstname must not contain numbers or special characters")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$", message = "Lastname must not contain numbers or special characters")
    private String lastName;

    @Pattern(regexp = "\\d{11}", message = "PESEL must be exactly 11 digits")
    @NotNull
    private String pesel;

    @NotNull
    private LocalDate dateOfBirth;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Pattern(regexp = "\\d{9,}", message = "Phone number must be at least 9 digits")
    private String mobileNumber;


    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    @JsonIgnore
    private Set<Policy> policies;


}





