package ru.itis.sem_1.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class HttpControllerException extends RuntimeException{
    private HttpStatus status;
    private String message;
}
