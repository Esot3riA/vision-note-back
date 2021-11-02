package org.swm.vnb.util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.swm.vnb.model.FileVO;
import org.swm.vnb.service.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class FileSaveUtil {

    private final FileService fileService;

    @Value("${storage.s3.api-server-path}")
    private String localStoragePath;

    @Autowired
    public FileSaveUtil(FileService fileService) {
        this.fileService = fileService;
    }

    public FileVO saveAudio(MultipartFile audioFile) throws IOException {
        FileVO fileInfo = saveToLocalStorage(audioFile, "audio")
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return fileInfo;
    }

    public void deleteAudio(Integer audioFileId) {
        FileVO audioFile = fileService.getFile(audioFileId);

        deleteToLocalStorage(audioFile.getSavedName(), "audio");
        fileService.deleteFile(audioFileId);
    }

    public FileVO saveImage(MultipartFile imageFile) throws IOException {
        FileVO fileInfo = saveToLocalStorage(imageFile, "avatar")
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return fileInfo;
    }

    public void deleteImage(String imageFileName) {
        deleteToLocalStorage(imageFileName, "avatar");
    }

    private Optional<FileVO> saveToLocalStorage(MultipartFile file, String subDirectory) throws IOException {
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

            FileVO fileInfo = saveFileInfo(file, file.getOriginalFilename());
            return Optional.of(fileInfo);
        }

        return Optional.empty();
    }

    private void deleteToLocalStorage(String fileName, String subDirectory) {
        StringBuilder builder = new StringBuilder()
                .append(localStoragePath)
                .append("/")
                .append(subDirectory)
                .append("/")
                .append(fileName);

        File localFile = new File(builder.toString());
        if (localFile.exists()) {
            localFile.delete();
        }
    }

    private FileVO saveFileInfo(MultipartFile savedFile, String originalFileName) {
        FileVO fileInfo = FileVO.builder()
                .originalName(originalFileName)
                .savedName(savedFile.getName())
                .extension(FilenameUtils.getExtension(savedFile.getOriginalFilename()))
                .path(savedFile.getOriginalFilename())
                .size(savedFile.getSize())
                .build();

        return fileService.createFile(fileInfo);
    }
}
