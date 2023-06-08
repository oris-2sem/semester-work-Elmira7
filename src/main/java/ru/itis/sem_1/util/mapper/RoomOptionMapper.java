package ru.itis.sem_1.util.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.sem_1.dto.RoomOptionRequest;
import ru.itis.sem_1.dto.RoomOptionResponse;
import ru.itis.sem_1.model.RoomOption;
import ru.itis.sem_1.repository.RoomOptionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomOptionMapper {

    private final RoomOptionRepository roomOptionRepository;

    public RoomOption toRoomOption(RoomOptionRequest roomOptionRequest){
        return RoomOption.builder()
                .name(roomOptionRequest.getName())
                .description(roomOptionRequest.getDescription())
                .build();
    }

    public List<RoomOption> toRoomOptionList(List<RoomOptionRequest> names){
        return names.stream().map(this::toRoomOption).collect(Collectors.toList());
    }

    public List<RoomOptionResponse> toRoomOptionResponseList(List<RoomOption> options){
        return options.stream().map(this::toRoomOptionResponse).collect(Collectors.toList());
    }

    public RoomOptionResponse toRoomOptionResponse(RoomOption roomOption){
        return RoomOptionResponse.builder()
                .createdAt(roomOption.getCreatedAt().getEpochSecond())
                .updatedAt(roomOption.getUpdatedAt().getEpochSecond())
                .id(roomOption.getId())
                .name(roomOption.getName())
                .description(roomOption.getDescription())
                .build();
    }
}
