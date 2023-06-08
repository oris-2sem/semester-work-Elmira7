package ru.itis.sem_1.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.sem_1.dto.*;
import ru.itis.sem_1.exception.HttpControllerException;
import ru.itis.sem_1.model.Room;
import ru.itis.sem_1.model.RoomOption;
import ru.itis.sem_1.model.RoomType;
import ru.itis.sem_1.model.User;
import ru.itis.sem_1.repository.RoomOptionRepository;
import ru.itis.sem_1.repository.RoomRepository;
import ru.itis.sem_1.repository.RoomTypeRepository;
import ru.itis.sem_1.repository.UserRepository;
import ru.itis.sem_1.util.mapper.RoomMapper;
import ru.itis.sem_1.util.mapper.RoomTypeMapper;
import ru.itis.sem_1.util.mapper.UserMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomMapper roomMapper;
    private final RoomTypeMapper roomTypeMapper;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RoomOptionRepository roomOptionRepository;
    private final NotificationService notificationService;

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Room room = roomMapper.toRoom(roomRequest);
        User user = null;
        RoomType roomType = roomTypeRepository.findByName(roomRequest.getRoomType()).orElseThrow(() -> new HttpControllerException(HttpStatus.BAD_REQUEST, "RoomType not found"));
        if (roomRequest.getOwner().getId() != null){
            user = userRepository.findById(roomRequest.getOwner().getId()).orElseThrow(() -> new HttpControllerException(HttpStatus.BAD_REQUEST, "User not found"));
        } else if (roomRequest.getOwner().getEmail() != null){
            user = userRepository.findByEmail(roomRequest.getOwner().getEmail()).orElseThrow(() -> new HttpControllerException(HttpStatus.BAD_REQUEST, "User not found"));
        }

        List<RoomOption> roomOptions = roomRequest.getRoomOption().stream()
                .map((request) -> roomOptionRepository.findByName(request.getName()).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "Услуга для дома не найдена")))
                .toList();
        room.setServices(roomOptions);
        room.setRoomType(roomType);
        room.setOwner(user);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }


    @Override
    public RoomResponse updateRoom(RoomRequest roomRequest) {
        return null;
    }

    @Override
    public void deleteRoom(UUID uuid) {

    }

    @Override
    public RoomResponse getRoomById(UUID uuid) {
        return roomMapper.toRoomResponse(roomRepository.findById(uuid).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "Помещение не найдено")));
    }

    @Override
    public List<RoomResponse> getRoomOptionalParameter(Map<String, String> parameters) {
        return roomMapper.toRoomResponseList(roomRepository.getRoomOptionalParameter(parameters));
    }

    @Override
    public List<RoomResponse> getRoomByOwnerPrincipal(String principal) {
        User user = userRepository.findByEmail(principal).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return getRoomOptionalParameter(Map.of("owner", user.getId().toString()));
    }

    @Override
    public List<RoomResponse> getRentedRoomByRenterPrincipal(String principal) {
        User user = userRepository.findByEmail(principal).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        //return roomMapper.toRoomResponseList(roomRepository.findRoomByRenters(List.of(user)));
        return null;
    }

    @Override
    public List<RoomResponse> getAllRoom() {
        return roomMapper.toRoomResponseList(roomRepository.findAll());
    }

    @Override
    public List<RoomTypeResponse> getAllRoomTypeResponse() {
        return roomTypeRepository.findAll().stream().map(roomTypeMapper::toRoomTypeResponse).collect(Collectors.toList());
    }

    @Override
    public RoomTypeResponse addRoomType(RoomTypeRequest roomTypeRequest) {
        if (roomTypeRepository.findByName(roomTypeRequest.getName()).isPresent()) throw new HttpControllerException(HttpStatus.BAD_REQUEST, "RoomType already exist");
        RoomType roomType = roomTypeMapper.toRoomType(roomTypeRequest);
        return roomTypeMapper.toRoomTypeResponse(roomTypeRepository.save(roomType));
    }

    @Override
    @Transactional
    public void deleteRoomType(UUID uuid) {
        RoomType roomType = roomTypeRepository.findById(uuid).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "RoomType not found"));
        roomTypeRepository.delete(roomType);
    }

    @Override
    @Transactional
    public void addRoomRenter(AddRenterRequest addRenterRequest) {
        Room room = roomRepository.findById(addRenterRequest.getIdRoom()).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "House not found"));
        User user = userRepository.findByEmail(addRenterRequest.getEmail()).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "User not found"));

        notificationService.addNotification(NotificationRequest.builder()
                        .body("Добавлен новый арендатор")
                        .payload(LocalDateTime.now().toString())
                        .theme("Новый арендатор")
                        .room(RoomRequest.builder().id(room.getId()).build())
                        .sender(UserRequest.builder().id(user.getId()).build())
                        .build());
        room.getRenters().add(user);
        roomRepository.save(room);
    }

    @Override
    public List<UserResponse> getRenterByRoomId(UUID uuid) {
        return roomRepository.findById(uuid).get().getRenters().stream().map(userMapper::toResponse).toList();
    }
}
