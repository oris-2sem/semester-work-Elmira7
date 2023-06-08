package ru.itis.sem_1.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExist extends HttpControllerException{

    public EmailAlreadyExist(HttpStatus status, String message) {
        super(status, message);
    }
}
