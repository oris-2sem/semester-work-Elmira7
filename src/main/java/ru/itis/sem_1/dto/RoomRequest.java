package ru.itis.sem_1.dto;

import lombok.*;
import ru.itis.sem_1.model.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    private UUID id;

    private String name;
    private Integer countRoom;
    private String description;
    private Integer square;

    private String roomType;

    private String status;
    private String imagePath;


    private String city;
    private String address;

    private List<RoomOptionRequest> roomOption;


    private UserRequest owner;

    private Instant createdAt;

    private Instant updatedAt;
}
