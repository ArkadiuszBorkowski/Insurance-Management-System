package pl.borkowskiarkadiusz.insurancemanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPesel(String pesel);

    Page<Client> findByPeselContainingOrLastNameContaining(String pesel, String lastName, Pageable pageable);
}
