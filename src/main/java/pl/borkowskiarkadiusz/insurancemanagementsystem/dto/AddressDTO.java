package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object for address information.
 * Used with Clients (entity and DTO).
 */

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {
    private String street;
    private String streetNo;
    private String apartmentNo;
    private String city;
    private String zipcode;
}