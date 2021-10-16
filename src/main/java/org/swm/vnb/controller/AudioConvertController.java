package org.swm.vnb.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.swm.vnb.util.AudioFileUtil;

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

    private final AudioFileUtil audioFileUtil;

    @Autowired
    public AudioConvertController(AudioFileUtil audioFileUtil) {
        this.audioFileUtil = audioFileUtil;
    }

    @PostMapping("/audio")
    public ResponseEntity convertAudio(@RequestParam("audio")MultipartFile audio) throws IOException {
        String fileUrl = audioFileUtil.save(audio);

        JsonObject responseObj = new JsonObject();
        responseObj.addProperty("file", fileUrl);

        return ResponseEntity.ok().body(fileUrl);
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

        return ResponseEntity.ok()
                .body(decodeUnicode(result.body()));
    }

    private String decodeUnicode(String rawString) {
        return StringEscapeUtils.unescapeJava(rawString);
    }
}
