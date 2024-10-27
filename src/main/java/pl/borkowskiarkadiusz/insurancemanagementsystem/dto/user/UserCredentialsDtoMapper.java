package pl.borkowskiarkadiusz.insurancemanagementsystem.dto.user;

import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.user.User;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.user.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

public class UserCredentialsDtoMapper {
    public static UserCredentialsDto map(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        Set<String> roles = user.getRoles()
                .stream()
                .map(UserRole::getName)
                .collect(Collectors.toSet());
        return new UserCredentialsDto(email, password, roles);
    }
}