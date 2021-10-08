package org.swm.vnb.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.swm.vnb.util.S3Uploader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

@RestController
@RequestMapping("/v1")
@Api(tags = {"오디오 변환 API"})
public class AudioConvertController {

    private final S3Uploader s3Uploader;

    @Autowired
    public AudioConvertController(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @PostMapping("/audio")
    public ResponseEntity convertAudio(@RequestParam("audio")MultipartFile audio) throws IOException {
        s3Uploader.upload(audio, "static");

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/audio/test")
    public ResponseEntity convertAudioTest() throws Exception {
        URI audioUri = ClassLoader.getSystemResource("testfiles/brainstorming_sentence.wav").toURI();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://stt.visionnote.io/client/dynamic/recognize"))
                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(audioUri)))
                .build();

        HttpResponse<String> result = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return ResponseEntity.ok().body(result.body());
    }
}
