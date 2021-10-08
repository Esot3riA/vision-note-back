package org.swm.vnb.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File localFile = convertToLocalFile(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        String fileName = dirName + "/" + UUID.randomUUID() + localFile.getName();
        String fileUrl = putS3(localFile, fileName);

        removeLocalFile(localFile);
        return fileUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeLocalFile(File localFile) {
        if (localFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convertToLocalFile(MultipartFile file) throws IOException {
        File localFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (localFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(localFile)) { // Byte Stream으로 저장
                fos.write(file.getBytes());
            }
            return Optional.of(localFile);
        }

        return Optional.empty();
    }
}
