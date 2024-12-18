package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.user.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}