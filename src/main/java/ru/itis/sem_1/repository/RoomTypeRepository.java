package ru.itis.sem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.sem_1.model.RoomType;

import java.util.Optional;
import java.util.UUID;

public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {
    Optional<RoomType> findByName(String name);
}
