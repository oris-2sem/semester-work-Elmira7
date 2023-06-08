package ru.itis.sem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.sem_1.model.CustomFile;

import java.util.UUID;

public interface FileRepository extends JpaRepository<CustomFile, UUID> {
}
