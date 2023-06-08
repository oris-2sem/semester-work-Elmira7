package ru.itis.sem_1.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.sem_1.dto.UserSignRequest;
import ru.itis.sem_1.dto.UserResponse;
import ru.itis.sem_1.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{uuid}")
    public UserResponse getUserResponseById(@PathVariable UUID uuid){
        return userService.getUserById(uuid);
    }

    @GetMapping("/{email}")
    public UserResponse getUserResponseByEmail(@PathVariable String email){
        return null;
    }

    @GetMapping
    public List<UserResponse> getUserResponseList(){
        return userService.getUserResponse();
    }

    @PostMapping
    public UserResponse registrationUser(@RequestBody UserSignRequest userRequest){
        return userService.createUser(userRequest);
    }

    @PatchMapping
    public UserResponse updateUser(@RequestBody UserSignRequest userRequest){
        return null;
    }

    @DeleteMapping("/{uuid}")
    public UserResponse deleteUser(@PathVariable UUID uuid){
        return null;
    }



}
