package ru.itis.sem_1.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.sem_1.dto.FileRequest;
import ru.itis.sem_1.dto.FileResponse;
import ru.itis.sem_1.repository.FileRepository;
import ru.itis.sem_1.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FilesController {

    private final FileService fileService;

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws IOException {

        FileResponse fileResponse = fileService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileResponse.getContentType()))
                .body(fileResponse.getBody());
    }

}
