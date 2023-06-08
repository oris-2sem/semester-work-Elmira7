package ru.itis.sem_1.service;

import ru.itis.sem_1.controller.RoomController;
import ru.itis.sem_1.dto.*;
import ru.itis.sem_1.model.RoomOption;
import ru.itis.sem_1.model.RoomType;
import ru.itis.sem_1.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface RoomService {

    RoomResponse createRoom(RoomRequest roomRequest);

    RoomResponse updateRoom(RoomRequest roomRequest);
    void deleteRoom(UUID uuid);
    RoomResponse getRoomById(UUID uuid);

    List<RoomResponse> getRoomOptionalParameter(Map<String, String> parameters);

    List<RoomResponse> getRoomByOwnerPrincipal(String principal);
    List<RoomResponse> getRentedRoomByRenterPrincipal(String principal);

    List<RoomResponse> getAllRoom();




    List<RoomTypeResponse> getAllRoomTypeResponse();
    RoomTypeResponse addRoomType(RoomTypeRequest roomTypeRequest);
    void deleteRoomType(UUID uuid);

    void addRoomRenter(AddRenterRequest addRenterRequest);

    List<UserResponse> getRenterByRoomId(UUID uuid);

}
