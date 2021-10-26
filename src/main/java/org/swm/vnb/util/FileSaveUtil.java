package org.swm.vnb.util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSaveUtil {

    @Value("${storage.s3.api-server-path}")
    private String localStoragePath;

    public String saveAudio(MultipartFile audioFile) throws IOException {
        File localFile = saveToLocalStorage(audioFile, "audio")
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return localFile.getName();
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        File localFile = saveToLocalStorage(imageFile, "avatar")
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return localFile.getName();
    }

    private Optional<File> saveToLocalStorage(MultipartFile file, String subDirectory) throws IOException {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        StringBuilder builder = new StringBuilder()
                .append(localStoragePath)
                .append("/")
                .append(subDirectory)
                .append("/")
                .append(fileName);

        File localFile = new File(builder.toString());
        if (localFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(localFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(localFile);
        }

        return Optional.empty();
    }
}
