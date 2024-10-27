package pl.borkowskiarkadiusz.insurancemanagementsystem.service.user;

import org.springframework.stereotype.Service;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.user.UserCredentialsDto;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.user.UserCredentialsDtoMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserCredentialsDtoMapper::map);
    }
}