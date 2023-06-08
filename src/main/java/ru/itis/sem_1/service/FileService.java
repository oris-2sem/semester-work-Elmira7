package ru.itis.sem_1.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.sem_1.dto.FileRequest;
import ru.itis.sem_1.dto.FileResponse;

public interface FileService {

    FileResponse addFile(FileRequest fileRequest);
    FileResponse getFile(String path);
}
