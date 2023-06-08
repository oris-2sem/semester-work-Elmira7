package ru.itis.sem_1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.sem_1.dto.NotificationRequest;
import ru.itis.sem_1.dto.NotificationResponse;
import ru.itis.sem_1.dto.RoomResponse;
import ru.itis.sem_1.dto.UserRequest;
import ru.itis.sem_1.model.Notification;
import ru.itis.sem_1.model.Room;
import ru.itis.sem_1.model.User;
import ru.itis.sem_1.repository.NotificationRepository;
import ru.itis.sem_1.repository.RoomRepository;
import ru.itis.sem_1.repository.UserRepository;
import ru.itis.sem_1.util.mapper.NotificationMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<NotificationResponse> getNotificationOfRoom(UUID houseId) {
        Room room = roomRepository.findById(houseId).orElseThrow();

        return notificationMapper.toNotificationResponseList(notificationRepository.findAllByRoom(room));

    }

    @Override
    public List<NotificationResponse> getNotificationOfUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();

        List<Room> rooms = roomRepository.getRoomOptionalParameter(Map.of("owner", user.getId().toString()));

        return rooms.stream()
                .map((room) -> getNotificationOfRoom(room.getId()))
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public NotificationResponse addNotification(NotificationRequest notificationRequest) {
        User sender = null;
        Room room = null;

        String email = notificationRequest.getSender().getEmail();
        UUID id = notificationRequest.getSender().getId();
        UUID idRoom = notificationRequest.getRoom().getId();

        if (email != null){
            sender = userRepository.findByEmail(email).orElseThrow();
        } else if (id != null) {
            sender = userRepository.findById(id).orElseThrow();
        }
        if (idRoom != null){
            room = roomRepository.findById(idRoom).orElseThrow();
        }

        Notification notification = notificationMapper.toNotification(notificationRequest);
        notification.setRoom(room);
        notification.setSender(sender);
        return notificationMapper.toNotificationResponse(notificationRepository.save(notification));
    }
}
