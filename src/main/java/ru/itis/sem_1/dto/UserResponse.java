package ru.itis.sem_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String city;
    private String email;
    private String firstName;
    private String lastName;
    private long createdAt;
    private long updatedAt;
}
