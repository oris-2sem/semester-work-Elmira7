package ru.itis.sem_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignRequest {

    @Email(message = "неверный формат почты")
    @NotBlank
    private String email;

    @Size(min = 4, max = 30, message = "пароль должен содержать не менее 4 символов")
    private String password;

}
