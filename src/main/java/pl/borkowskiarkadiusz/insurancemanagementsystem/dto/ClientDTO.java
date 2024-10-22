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

    public void setPesel(String pesel) {
        this.pesel = pesel;
        this.dateOfBirth = extractBirthDateFromPesel(pesel);
    }


    private LocalDate extractBirthDateFromPesel(String pesel) {
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        if (month > 80) {
            year += 1800;
            month -= 80;
        } else if (month > 60) {
            year += 2200;
            month -= 60;
        } else if (month > 40) {
            year += 2100;
            month -= 40;
        } else if (month > 20) {
            year += 2000;
            month -= 20;
        } else {
            year += 1900;
        }
        return LocalDate.of(year, month, day);
    }
}