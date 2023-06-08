package ru.itis.sem_1.service;


import ru.itis.sem_1.dto.UserRequest;
import ru.itis.sem_1.dto.UserSignRequest;
import ru.itis.sem_1.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse getUserById(UUID uuid);

    UserResponse getUserByEmail(String email);

    List<UserResponse> getUserResponse();
    UserResponse createUser(UserSignRequest userRequest);
    UserResponse updateUser(UserRequest userRequest);
}
