package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {
    private String firstName;
    private String lastName;
    private String pesel;
    private LocalDate dateOfBirth;
    private String email;
    private String mobileNumber;
    private AddressDTO address;
}