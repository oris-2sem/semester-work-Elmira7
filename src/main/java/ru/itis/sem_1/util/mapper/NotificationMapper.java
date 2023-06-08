package ru.itis.sem_1.util.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.sem_1.dto.NotificationRequest;
import ru.itis.sem_1.dto.NotificationResponse;
import ru.itis.sem_1.model.Notification;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificationMapper {

    private final UserMapper userMapper;
    private final RoomMapper roomMapper;

    public Notification toNotification(NotificationRequest notificationRequest){
        return Notification.builder()
                .body(notificationRequest.getBody())
                .payload(notificationRequest.getPayload())
                .theme(notificationRequest.getTheme())
                .build();
    }

    public NotificationResponse toNotificationResponse(Notification notification){
        return NotificationResponse.builder()
                .body(notification.getBody())
                .payload(notification.getPayload())
                .theme(notification.getTheme())
                .sender(userMapper.toResponse(notification.getSender()))
                .room(roomMapper.toRoomResponse(notification.getRoom()))
                .build();
    }

    public List<NotificationResponse> toNotificationResponseList(List<Notification> notificationList){
        return notificationList.stream().map(this::toNotificationResponse).toList();
    }
}
