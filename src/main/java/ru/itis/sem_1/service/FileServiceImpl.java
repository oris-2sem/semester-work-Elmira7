package ru.itis.sem_1.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.sem_1.dto.FileRequest;
import ru.itis.sem_1.dto.FileResponse;
import ru.itis.sem_1.exception.HttpControllerException;
import ru.itis.sem_1.model.CustomFile;
import ru.itis.sem_1.repository.FileRepository;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    @Override
    public FileResponse addFile(FileRequest fileRequest) {
        try {


            String path = new File(".").getCanonicalFile() + File.separator + "files" + File.separator;

            File fileSaveDir = new File(path);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            String fileName = fileRequest.getName();
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

            CustomFile customFile = CustomFile.builder()
                    .contentType(fileRequest.getContentType())
                    .extension(extension)
                    .name(name)
                    .build();

            CustomFile savedCustomFile = fileRepository.save(customFile);

            Files.copy(fileRequest.getInputStream(), Path.of(path, customFile.getId().toString() + "." + customFile.getExtension()), StandardCopyOption.REPLACE_EXISTING);

            return FileResponse.builder()
                    .contentType(savedCustomFile.getContentType())
                    .name(savedCustomFile.getName())
                    .id(savedCustomFile.getId())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public FileResponse getFile(String fileRequestName) {
        try {

        String path = new File(".").getCanonicalFile() + File.separator + "files" + File.separator;

        CustomFile customFile = fileRepository.findById(UUID.fromString(fileRequestName)).orElseThrow(() -> new HttpControllerException(HttpStatus.NOT_FOUND, "Files not found"));

        File file = new File(path + customFile.getId() + "." + customFile.getExtension());
        byte[] content = Files.readAllBytes(file.toPath());

        return FileResponse.builder()
                .contentType(customFile.getContentType())
                .body(content)
                .name(customFile.getName())
                .extension(customFile.getExtension())
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
