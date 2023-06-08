package ru.itis.sem_1.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.sem_1.dto.*;
import ru.itis.sem_1.model.CustomFile;
import ru.itis.sem_1.model.User;
import ru.itis.sem_1.service.FileService;
import ru.itis.sem_1.service.NotificationService;
import ru.itis.sem_1.service.RoomService;
import ru.itis.sem_1.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final FileService fileService;

    private final NotificationService notificationService;

    @GetMapping
    public String getRoomByOptionalParameter(@RequestParam Map<String, String> parameters,
                                             Model model){

        model.addAttribute("rooms", roomService.getRoomOptionalParameter(parameters));
        return "search";
    }

    @GetMapping("/your")
    public String getRoomYourself(Model model,
                                  Principal principal){
        model.addAttribute("rooms", roomService.getRoomByOwnerPrincipal(principal.getName()));
        return "search";
    }

    @GetMapping("/add/renter/{houseId}")
    public String getRoomAddRenter(@PathVariable UUID houseId,
                                   Model model){
        model.addAttribute("room", roomService.getRoomById(houseId));
        model.addAttribute("addRenterRequest", new AddRenterRequest());
        return "addRenter";
    }

    @PostMapping("/add/renter/{houseId}")
    public String addRoomRenter(@PathVariable UUID houseId,
                                @Valid AddRenterRequest addRenterRequest){
        addRenterRequest.setIdRoom(houseId);
        roomService.addRoomRenter(addRenterRequest);

        return "redirect:/user/profile";
    }

    @GetMapping("/add")
    public String roomCreatePage(Model model){
        model.addAttribute("typeRoom", roomService.getAllRoomTypeResponse());
        model.addAttribute("roomRequest", new RoomRequest());
        return "roomCreate";
    }

    @PostMapping("/add")
    public String roomCreate(@Valid RoomRequest roomRequest,
                             MultipartFile file,
                             Principal principal,
                             BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "roomCreate";
        }

        roomRequest.setOwner(UserRequest.builder().email(principal.getName()).build());
        roomRequest.setRoomOption(new ArrayList<>());
        FileRequest fileRequest = FileRequest.builder()
                .name(file.getOriginalFilename())
                .inputStream(file.getInputStream())
                .contentType(file.getContentType())
                .build();

        file.getName();
        if (!file.getName().equals("")){
            FileResponse fileResponse = fileService.addFile(fileRequest);
            roomRequest.setImagePath(fileResponse.getId().toString());
        }
        roomService.createRoom(roomRequest);
        return "profile";
    }

    @GetMapping("/owner/{houseId}")
    public String getOwnerRoomPage(@PathVariable UUID houseId,
                                   Model model){
        model.addAttribute("notification", notificationService.getNotificationOfRoom(houseId));
        model.addAttribute("renter", roomService.getRenterByRoomId(houseId));
        model.addAttribute("room", roomService.getRoomById(houseId));
        return "roomOwner";
    }

    @GetMapping("/renter/{houseId}")
    public String getRenterRoomPage(@PathVariable UUID houseId,
                                    Principal principal,
                                    Model model){
        model.addAttribute("room", roomService.getRentedRoomByRenterPrincipal(principal.getName()));
        return "roomRenter";
    }

}
