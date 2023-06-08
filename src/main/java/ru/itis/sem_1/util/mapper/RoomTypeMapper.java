package ru.itis.sem_1.util.mapper;

import org.springframework.stereotype.Component;
import ru.itis.sem_1.dto.RoomTypeRequest;
import ru.itis.sem_1.dto.RoomTypeResponse;
import ru.itis.sem_1.model.RoomType;

@Component
public class RoomTypeMapper {
    public RoomType toRoomType(RoomTypeRequest roomTypeRequest){
        return RoomType.builder()
                .name(roomTypeRequest.getName())
                .description(roomTypeRequest.getDescription())
                .build();
    }

    public RoomTypeResponse toRoomTypeResponse(RoomType roomType){
        return RoomTypeResponse.builder()
                .id(roomType.getId())
                .description(roomType.getDescription())
                .name(roomType.getName())
                .build();
    }
}
