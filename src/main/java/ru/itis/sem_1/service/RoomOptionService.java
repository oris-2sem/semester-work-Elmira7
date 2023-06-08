package ru.itis.sem_1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.sem_1.dto.RoomOptionRequest;
import ru.itis.sem_1.dto.RoomOptionResponse;
import ru.itis.sem_1.dto.RoomTypeRequest;
import ru.itis.sem_1.model.RoomOption;
import ru.itis.sem_1.repository.RoomOptionRepository;

import java.util.List;
import java.util.UUID;

public interface RoomOptionService {

    RoomOptionResponse createRoomOption(RoomOptionRequest roomOptionRequest);
    RoomOptionResponse deleteRoomOption(UUID uuid);

    RoomOptionResponse addRoomOptionToRoom(UUID idRoom);
    void deleteRoomOptionFromRoom(UUID idRoom, UUID idRoomType);

    List<RoomOptionResponse> getAllRoomOptionResponse();


}
