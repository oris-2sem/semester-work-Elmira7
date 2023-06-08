package ru.itis.sem_1.service;

import ru.itis.sem_1.dto.NotificationRequest;
import ru.itis.sem_1.dto.NotificationResponse;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<NotificationResponse> getNotificationOfRoom(UUID houseId);
    List<NotificationResponse> getNotificationOfUser(UUID userId);
    NotificationResponse addNotification(NotificationRequest notificationRequest);
}
