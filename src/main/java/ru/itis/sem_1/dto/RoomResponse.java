package ru.itis.sem_1.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.sem_1.model.Room;
import ru.itis.sem_1.model.RoomType;
import ru.itis.sem_1.model.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private UUID id;

    private String name;
    private Integer countRoom;
    private String description;
    private Integer square;

    private RoomTypeResponse roomType;

    private String status;
    private String imagePath;


    private String city;
    private String address;

    private List<RoomOptionResponse> roomOption;

    private UserResponse owner;

    private long createdAt;

    private long updatedAt;
}
