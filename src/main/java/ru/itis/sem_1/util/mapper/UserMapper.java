package ru.itis.sem_1.util.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.sem_1.dto.UserRequest;
import ru.itis.sem_1.dto.UserSignRequest;
import ru.itis.sem_1.dto.UserResponse;
import ru.itis.sem_1.model.User;


@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toSignUser(UserSignRequest userRequest){
        return User.builder()
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .role(User.Role.USER)
                .build();
    }

    public User toUser(UserRequest userRequest){
        return User.builder()
                .id(userRequest.getId())
                .email(userRequest.getEmail())
                .city(userRequest.getCity())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .build();
    }

    public UserResponse toResponse(User user){
        return UserResponse.builder()
                .city(user.getCity())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt().getEpochSecond())
                .updatedAt(user.getUpdatedAt().getEpochSecond())
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
