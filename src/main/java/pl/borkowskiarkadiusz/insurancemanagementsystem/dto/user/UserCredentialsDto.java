package pl.borkowskiarkadiusz.insurancemanagementsystem.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

/**
 * Data transfer object for user credentials.
 */
@Getter
@AllArgsConstructor
public class UserCredentialsDto {
    private final String email;
    private final String password;
    private final Set<String> roles;
}