package ru.itis.sem_1.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.sem_1.dto.*;
import ru.itis.sem_1.model.RoomOption;
import ru.itis.sem_1.model.RoomType;
import ru.itis.sem_1.service.RoomOptionService;
import ru.itis.sem_1.service.RoomService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/room")
@AllArgsConstructor
public class RoomRestController {

    private final RoomService roomService;
    private final RoomOptionService roomOptionService;

    @PostMapping
    public RoomResponse createRoom(@RequestBody RoomRequest roomRequest){
        return roomService.createRoom(roomRequest);
    }

    @PutMapping
    public RoomResponse updateRoom(@RequestBody RoomRequest roomRequest){
        return roomService.updateRoom(roomRequest);
    }

    @DeleteMapping("/{uuid}")
    public void deleteRoom(@PathVariable UUID uuid){
        roomService.deleteRoom(uuid);
    }

    @GetMapping("/{uuid}")
    RoomResponse getRoomById(@PathVariable UUID uuid){
        return roomService.getRoomById(uuid);
    }

    @GetMapping
    List<RoomResponse> getResponseList(){
        return roomService.getAllRoom();
    }

    @GetMapping("/filter")
    List<RoomResponse> getRoomByOptionalParameter(@RequestParam Map<String, String> parameters){
        return roomService.getRoomOptionalParameter(parameters);
    }


    @GetMapping("/type")
    List<RoomTypeResponse> getAllRoomType(){
        return roomService.getAllRoomTypeResponse();
    }

    @PostMapping("/type")
    RoomTypeResponse addRoomType(@RequestBody RoomTypeRequest roomType){
        return roomService.addRoomType(roomType);
    }

    @DeleteMapping("/type/{uuid}")
    void deleteRoomType(@PathVariable UUID uuid){
        roomService.deleteRoomType(uuid);
    }


    @GetMapping("/option")
    List<RoomOptionResponse> getAllRoomOption(){
        return roomOptionService.getAllRoomOptionResponse();
    }

    @PostMapping("/option")
    RoomOptionResponse createRoomOption(@RequestBody RoomOptionRequest roomOptionRequest){
        return roomOptionService.createRoomOption(roomOptionRequest);
    }

    @DeleteMapping("/option/{uuid}")
    void deleteRoomOption(@PathVariable UUID uuid){
        roomOptionService.deleteRoomOption(uuid);
    }

//    List<RoomResponse> getRoomByOwner(String idOwner);
//    List<RoomResponse> getRoomByAddress(String address);
//    List<RoomResponse> getRoomByRenter(String idRenter);
//    List<RoomResponse> getRoomByCity(String city);
//    List<RoomOption> getRoomService(UUID idRoom);
}
