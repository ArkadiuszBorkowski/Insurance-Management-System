package pl.borkowskiarkadiusz.insurancemanagementsystem.dto;

import java.util.Date;
import java.util.Objects;

public class ClientDTO {
    private String firstName;
    private String lastName;
    private String pesel;
    private Date dateOfBirth;
    private String email;
    private String mobileNumber;
    private AddressDTO address;

    public ClientDTO() {
    }

    public ClientDTO(String firstName, String lastName, String pesel, Date dateOfBirth, String email, String mobileNumber, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(firstName, clientDTO.firstName) && Objects.equals(lastName, clientDTO.lastName) && Objects.equals(pesel, clientDTO.pesel) && Objects.equals(dateOfBirth, clientDTO.dateOfBirth) && Objects.equals(email, clientDTO.email) && Objects.equals(mobileNumber, clientDTO.mobileNumber) && Objects.equals(address, clientDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, pesel, dateOfBirth, email, mobileNumber, address);
    }
}