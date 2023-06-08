package ru.itis.sem_1.util.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.sem_1.dto.RoomRequest;
import ru.itis.sem_1.dto.RoomResponse;
import ru.itis.sem_1.model.Room;
import ru.itis.sem_1.model.RoomType;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomMapper {

    private final UserMapper userMapper;
    private final RoomOptionMapper roomOptionMapper;

    private final RoomTypeMapper roomTypeMapper;

    public Room toRoom(RoomRequest roomRequest){
        return Room.builder()
                .roomType(RoomType.builder().name(roomRequest.getRoomType()).build())
                .status(Room.Status.ACTIVE)
                .city(roomRequest.getCity())
                .address(roomRequest.getAddress())
                .countRoom(roomRequest.getCountRoom())
                .description(roomRequest.getDescription())
                .square(roomRequest.getSquare())
                .name(roomRequest.getName())
                .owner(userMapper.toUser(roomRequest.getOwner()))
                .imagePath(roomRequest.getImagePath())
                .services(roomOptionMapper.toRoomOptionList(roomRequest.getRoomOption()))
                .build();
    }

    public RoomResponse toRoomResponse(Room room){
        return RoomResponse.builder()
                .address(room.getAddress())
                .roomOption(roomOptionMapper.toRoomOptionResponseList(room.getServices()))
                .countRoom(room.getCountRoom())
                .city(room.getCity())
                .description(room.getDescription())
                .owner(userMapper.toResponse(room.getOwner()))
                .status(room.getStatus().name())
                .createdAt(room.getCreatedAt().getEpochSecond())
                .updatedAt(room.getUpdatedAt().getEpochSecond())
                .square(room.getSquare())
                .roomType(roomTypeMapper.toRoomTypeResponse(room.getRoomType()))
                .id(room.getId())
                .imagePath(room.getImagePath())
                .name(room.getName())
                .build();
    }

    public List<Room> toRoomList(List<RoomRequest> roomRequests){
        return roomRequests.stream().map(this::toRoom).collect(Collectors.toList());
    }

    public List<RoomResponse> toRoomResponseList(List<Room> rooms){
        return rooms.stream().map(this::toRoomResponse).collect(Collectors.toList());
    }
}
