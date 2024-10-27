package pl.borkowskiarkadiusz.insurancemanagementsystem.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserCredentialsDto {
    private final String email;
    private final String password;
    private final Set<String> roles;
}