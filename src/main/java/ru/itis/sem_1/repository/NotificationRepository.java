package ru.itis.sem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.sem_1.model.Notification;
import ru.itis.sem_1.model.Room;
import ru.itis.sem_1.model.User;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByRoom(Room room);

}
