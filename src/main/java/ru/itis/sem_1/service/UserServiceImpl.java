package ru.itis.sem_1.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.itis.sem_1.dto.UserRequest;
import ru.itis.sem_1.dto.UserSignRequest;
import ru.itis.sem_1.dto.UserResponse;
import ru.itis.sem_1.exception.EmailAlreadyExist;
import ru.itis.sem_1.exception.HttpControllerException;
import ru.itis.sem_1.model.User;
import ru.itis.sem_1.repository.UserRepository;
import ru.itis.sem_1.util.mapper.UserMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getUserById(UUID uuid) {
        return userMapper.toResponse(userRepository.findById(uuid).get());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userMapper.toResponse(userRepository.findByEmail(email).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "User with email not found")));
    }

    @Override
    public List<UserResponse> getUserResponse() {
        return null;
    }

    @Override
    public UserResponse createUser(UserSignRequest userRequest){
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) throw new EmailAlreadyExist(HttpStatus.NOT_FOUND, "Почта уже используется");
        User user = userMapper.toSignUser(userRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "User not found"));
        user.setCity(userRequest.getCity());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        return userMapper.toResponse(user);
    }
}
