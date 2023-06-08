package ru.itis.sem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.sem_1.model.Query;

import java.util.UUID;

public interface QueryRepository extends JpaRepository<Query, UUID> {

}
