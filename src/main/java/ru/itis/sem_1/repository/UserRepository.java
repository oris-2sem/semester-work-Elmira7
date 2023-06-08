package ru.itis.sem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.sem_1.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
