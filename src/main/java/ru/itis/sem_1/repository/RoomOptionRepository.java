package ru.itis.sem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.sem_1.model.RoomOption;

import java.util.Optional;
import java.util.UUID;

public interface RoomOptionRepository extends JpaRepository<RoomOption, UUID> {

    Optional<RoomOption> findByName(String name);
}
