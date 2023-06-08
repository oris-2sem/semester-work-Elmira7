package ru.itis.sem_1.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private String body;
    private String theme;
    private String payload;
    private RoomResponse room;
    private UserResponse sender;
}
