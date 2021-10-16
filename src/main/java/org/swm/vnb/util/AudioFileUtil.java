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
public class AudioFileUtil {

    @Value("${audio.path}")
    public String audioPath;

    public String save(MultipartFile multipartFile) throws IOException {
        File localFile = saveLocalFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return localFile.getName();
    }

    private Optional<File> saveLocalFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        File localFile = new File(audioPath + "/" + fileName);

        if (localFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(localFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(localFile);
        }

        return Optional.empty();
    }
}
