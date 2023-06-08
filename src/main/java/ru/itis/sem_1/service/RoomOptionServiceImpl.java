package ru.itis.sem_1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.sem_1.dto.RoomOptionRequest;
import ru.itis.sem_1.dto.RoomOptionResponse;
import ru.itis.sem_1.dto.RoomTypeRequest;
import ru.itis.sem_1.model.RoomOption;
import ru.itis.sem_1.repository.RoomOptionRepository;
import ru.itis.sem_1.repository.RoomRepository;
import ru.itis.sem_1.util.mapper.RoomOptionMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomOptionServiceImpl implements RoomOptionService {

    private final RoomOptionRepository roomOptionRepository;
    private final RoomOptionMapper roomOptionMapper;

    @Override
    public RoomOptionResponse createRoomOption(RoomOptionRequest roomTypeRequest) {
        RoomOption roomOption = roomOptionMapper.toRoomOption(roomTypeRequest);
        return roomOptionMapper.toRoomOptionResponse(roomOptionRepository.save(roomOption));
    }

    @Override
    public RoomOptionResponse deleteRoomOption(UUID uuid) {
        return null;
    }

    @Override
    public RoomOptionResponse addRoomOptionToRoom(UUID idRoom) {
        return null;
    }

    @Override
    public void deleteRoomOptionFromRoom(UUID idRoom, UUID idRoomType) {

    }

    @Override
    public List<RoomOptionResponse> getAllRoomOptionResponse() {
        return roomOptionRepository.findAll().stream().map(roomOptionMapper::toRoomOptionResponse).collect(Collectors.toList());
    }


}
