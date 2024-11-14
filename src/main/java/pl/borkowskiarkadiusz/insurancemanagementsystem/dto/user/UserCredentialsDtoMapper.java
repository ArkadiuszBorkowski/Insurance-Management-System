package pl.borkowskiarkadiusz.insurancemanagementsystem.dto.user;

import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.user.User;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.user.UserRole;

import java.util.Set;
import java.util.stream.Collectors;
/**
 * Mapper class for converting User entities to UserCredentialsDto.
 */
public class UserCredentialsDtoMapper {

    /**
     * Maps a User entity to a UserCredentialsDto.
     *
     * @param user the User entity to map
     * @return the mapped UserCredentialsDto
     */
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