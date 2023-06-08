package ru.itis.sem_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class FileResponse {
    private UUID id;
    private String name;
    private String extension;
    private String contentType;
    private String path;
    private byte[] body;
}
